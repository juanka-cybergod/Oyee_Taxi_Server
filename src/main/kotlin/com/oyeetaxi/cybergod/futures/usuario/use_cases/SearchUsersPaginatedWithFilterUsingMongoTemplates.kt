package com.oyeetaxi.cybergod.futures.usuario.use_cases

import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.models.requestFilter.UserFilterOptions
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterAdministradores
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterConductores
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterDeshabilitados
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterVerificacionesPendientes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import kotlin.math.min

class SearchUsersPaginatedWithFilterUsingMongoTemplates {

    @Autowired
    private val mongoTemplateRepository: MongoTemplate? = null


    operator fun invoke(search:String, userFilterOptions: UserFilterOptions?, pageable: Pageable): Page<Usuario> {

        val query = Query()

        query.addCriteria(
            Criteria().orOperator(
                Criteria.where("nombre").regex(search),
                Criteria.where("apellidos").regex(search),
                Criteria.where("telefonoMovil").regex(search),
                Criteria.where("correo").regex(search),
                Criteria.where("usuarioVerificacion.identificacion").regex(search),
            )
        )


        query.with( pageable.sort) //query.with(Sort.by(Sort.Direction.ASC, "nombre"))


        var allUserFound =  mongoTemplateRepository!!.find(query,Usuario::class.java)

        userFilterOptions?.let { userFilter ->

            with(userFilter) {
                condutores?.let { allUserFound = allUserFound.filterConductores(it) }
                deshabilitados?.let { allUserFound = allUserFound.filterDeshabilitados(it) }
                administradores?.let { allUserFound = allUserFound.filterAdministradores(it) }
                verificacionesPendientes?.let { allUserFound = allUserFound.filterVerificacionesPendientes(it) }
            }

        }



        val start = pageable.offset.toInt()
        val end = min(start + pageable.pageSize, allUserFound.size)
        return PageImpl(allUserFound.subList(start, end), pageable, allUserFound.size.toLong())
    }
}






/**
//In Sirve para Buscar en Arrays Enum y esas cosas
query.addCriteria(Criteria.where("roles").in("ADMIN"));
//Not In
query.addCriteria(Criteria.where("roles").nin("USER"));
//Es Igual a Eric
query.addCriteria(Criteria.where("name").isEqualTo("Eric"))
query.addCriteria(Criteria.where("name").`is`("Eric")) ???
query.addCriteria(Criteria.where("lastName").`is`("Paul"))
//AND
query.addCriteria(Criteria.where("firstName").is("Nivin").and("lastName").is("Paul"));
//Comienza con A
query.addCriteria(Criteria.where("name").regex("^A"));
//Termina con c
query.addCriteria(Criteria.where("name").regex("c$"));
//Mayor que 20 y Menor que 50
query.addCriteria(Criteria.where("age").lt(50).gt(20));
//Ordenar
query.with(Sort.by(Sort.Direction.ASC, "nombre"))
//Pageable
val pageableRequest: Pageable = PageRequest.of(0, 10)
.withSort(Sort.Direction.ASC,"nombre")
.withPage(0)
query.with(pageableRequest)
//And Operator
query.addCriteria(
Criteria().andOperator(
Criteria.where("field1").exists(true),
Criteria.where("field1").ne(false)
)
)
//Or Operator
query.addCriteria(
Criteria().orOperator(
Criteria.where("field1").exists(true),
Criteria.where("field1").ne(false)
)
)
 */