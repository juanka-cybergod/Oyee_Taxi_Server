package com.oyeetaxi.cybergod.futures.usuario.services

import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.models.requestFilter.UserFilterOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.and
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UsuarioQueryService {


    @Autowired
    private val mongoTemplateRepository: MongoTemplate? = null
    fun searchUsersFiltered(search:String, userFilterOptions: UserFilterOptions?, sort: Sort): List<Usuario> {

        val criteriasList :MutableList<Criteria> = ArrayList()
        if (userFilterOptions!=null) {
            userFilterOptions.condutores?.let { it ->
                criteriasList.add(Criteria.where("conductor").`is`(it))
            }
            userFilterOptions.deshabilitados?.let { it ->
                if (criteriasList.isEmpty()) {
                    criteriasList.add(Criteria.where("habilitado").`is`(!it))
                } else {
                    criteriasList.add(Criteria().andOperator(Criteria.where("habilitado").`is`(!it)))
                }

                }

        }



        val query = Query().apply {
            addCriteria(
                Criteria().orOperator(
                    Criteria.where("nombre").regex(search),
                    Criteria.where("apellidos").regex(search),
                    Criteria.where("telefonoMovil").regex(search),
                    Criteria.where("correo").regex(search),
                    Criteria.where("usuarioVerificacion.identificacion").regex(search),
                )
//                .andOperator(Criteria.where("conductor").`is`(true))  //OK Simple


                    .andOperator(criteriasList) //OK Si no Falta el 1er Valor del Array
            )

           with(sort)
        }



        println(query.toString())

        return  mongoTemplateRepository?.find(query,Usuario::class.java).orEmpty()

    }
}



/*
        query.addCriteria(
            Criteria().orOperator(
                Criteria.where("nombre").regex(search),
                Criteria.where("apellidos").regex(search),
                Criteria.where("telefonoMovil").regex(search),
                Criteria.where("correo").regex(search),
                Criteria.where("usuarioVerificacion.identificacion").regex(search),
            )
        )

        query.with(sort)//query.with(Sort.by(Sort.Direction.ASC, "nombre"))
 */


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