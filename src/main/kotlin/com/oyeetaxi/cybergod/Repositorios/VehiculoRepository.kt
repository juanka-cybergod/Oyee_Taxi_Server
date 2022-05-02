package com.oyeetaxi.cybergod.Repositorios


import com.oyeetaxi.cybergod.Modelos.Usuario
import com.oyeetaxi.cybergod.Modelos.Vehiculo
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

}