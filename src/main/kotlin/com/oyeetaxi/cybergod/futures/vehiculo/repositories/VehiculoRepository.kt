package com.oyeetaxi.cybergod.futures.vehiculo.repositories


import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VehiculoRepository: MongoRepository<Vehiculo, String> {

    @Query("{'habilitado': ?0, 'visible': ?1, 'activo': ?2}")
    fun findAviableVehicles(habilitado: Boolean = true,visible: Boolean = true,activo: Boolean = true) : List<Vehiculo>

    @Query("{'idUsuario': ?0, 'activo': ?1}")
    fun findActiveVehicleByUserId(idUsuario: String,activo: Boolean = true) : Optional<List<Vehiculo>>

    @Query("{'idUsuario': ?0}")
    fun findAllVehiclesByUserId(idUsuario: String) : List<Vehiculo>

    @Query("{\$or: [{'marca': {\$regex: ?0, \$options: 'i'}},{'modelo': {\$regex: ?0, \$options: 'i'}},{'ano': {\$regex: ?0, \$options: 'i'}},{'vehiculoVerificacion.matricula': {\$regex: ?0, \$options: 'i'}},{'vehiculoVerificacion.circulacion': {\$regex: ?0, \$options: 'i'}} ]}")
    fun searchAll(search:String,sort: Sort) : List<Vehiculo>



}