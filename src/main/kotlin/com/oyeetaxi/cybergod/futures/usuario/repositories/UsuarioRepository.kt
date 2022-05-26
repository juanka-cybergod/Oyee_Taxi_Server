package com.oyeetaxi.cybergod.futures.usuario.repositories


import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query //
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: MongoRepository<Usuario, String>{

    //Buscar por Numero de Telefono Movil
    @Query("{'telefonoMovil': ?0}")
    fun findUserByPhoneNumber(telefonoMovil: String) : Optional<Usuario>

    @Query("{'telefonoMovil': ?0}")
    fun findUserByPhoneNumberList(telefonoMovil: String) : Optional<List<Usuario>>

    @Query("{'correo': ?0}")
    fun findUserByEmail(correo: String) : Optional<List<Usuario>>


//    @Query("{\$or: [    {'nombre': {\$regex: ?1, \$options: 'i'}}   ,   {'apellidos': {\$regex: ?1, \$options: 'i'}}    ,    {'correo': {\$regex: ?1, \$options: 'i'}}     ,    {'telefonoMovil': {\$regex: ?1, \$options: 'i'}}  ]}")
    @Query("{\$or: [{'nombre': {\$regex: ?0, \$options: 'i'}},{'apellidos': {\$regex: ?0, \$options: 'i'}},{'telefonoMovil': {\$regex: ?0, \$options: 'i'}},{'correo': {\$regex: ?0, \$options: 'i'}} ,{'usuarioVerificacion.identificacion': {\$regex: ?0, \$options: 'i'}} ]}")
    fun search(search:String="",pageable: Pageable): Page<Usuario>


    @Query("{\$or: [{'nombre': {\$regex: ?0, \$options: 'i'}},{'apellidos': {\$regex: ?0, \$options: 'i'}},{'telefonoMovil': {\$regex: ?0, \$options: 'i'}},{'correo': {\$regex: ?0, \$options: 'i'}} ,{'usuarioVerificacion.identificacion': {\$regex: ?0, \$options: 'i'}} ]}")
    fun searchAll(search:String="",sort: Sort): List<Usuario>

//    @Query("{'nombre': ?1}")
//    override fun findAll(pageable: Pageable,nombre_correo_numero:String): Page<Usuario> {
//
//    }


}