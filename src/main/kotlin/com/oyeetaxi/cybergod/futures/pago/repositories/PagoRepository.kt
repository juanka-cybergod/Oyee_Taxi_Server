package com.oyeetaxi.cybergod.futures.pago.repositories


import com.oyeetaxi.cybergod.futures.pago.models.Pago
import com.oyeetaxi.cybergod.futures.pago.models.type.EstadoPago
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query //
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PagoRepository: MongoRepository<Pago, String>{


    @Query("{'idVehiculo': ?0,'estado': ?1}")
    fun getAllPaymentsByVehicleId(idVehiculo: String,estado:EstadoPago = EstadoPago.COMPLETADO) : Optional<List<Pago>>



//    @Query("{'telefonoMovil': ?0}")
//    fun findUserByPhoneNumber(telefonoMovil: String) : Optional<Usuario>

//    @Query("{'telefonoMovil': ?0}")
//    fun findUserByPhoneNumberList(telefonoMovil: String) : Optional<List<Usuario>>

//    @Query("{'telefonoMovil':{\$regex: ?0}}")
//    fun findUserByPhoneNumberList(telefonoMovil: String) : Usuario?
//
//    @Query("{'correo': ?0}")
//    fun findUserByEmail(correo: String) : Optional<List<Usuario>>
//
//    @Query("{\$or: [{'nombre': {\$regex: ?0, \$options: 'i'}},{'apellidos': {\$regex: ?0, \$options: 'i'}},{'telefonoMovil': {\$regex: ?0, \$options: 'i'}},{'correo': {\$regex: ?0, \$options: 'i'}} ,{'usuarioVerificacion.identificacion': {\$regex: ?0, \$options: 'i'}} ]}")
//    fun search(search:String="",pageable: Pageable): Page<Usuario>
//
//
//    @Query("{\$or: [{'nombre': {\$regex: ?0, \$options: 'i'}},{'apellidos': {\$regex: ?0, \$options: 'i'}},{'telefonoMovil': {\$regex: ?0, \$options: 'i'}},{'correo': {\$regex: ?0, \$options: 'i'}} ,{'usuarioVerificacion.identificacion': {\$regex: ?0, \$options: 'i'}} ]}")
//    fun searchAll(search:String,sort: Sort): List<Usuario>




}