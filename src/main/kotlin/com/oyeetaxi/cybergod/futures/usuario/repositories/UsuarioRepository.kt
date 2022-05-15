package com.oyeetaxi.cybergod.futures.usuario.repositories


import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: MongoRepository<Usuario, String> {

    //Buscar por Numero de Telefono Movil
    @Query("{'telefonoMovil': ?0}")
    fun findUserByPhoneNumber(telefonoMovil: String) : Optional<Usuario>

    @Query("{'telefonoMovil': ?0}")
    fun findUserByPhoneNumberList(telefonoMovil: String) : Optional<List<Usuario>>

    @Query("{'correo': ?0}")
    fun findUserByEmail(correo: String) : Optional<List<Usuario>>

}