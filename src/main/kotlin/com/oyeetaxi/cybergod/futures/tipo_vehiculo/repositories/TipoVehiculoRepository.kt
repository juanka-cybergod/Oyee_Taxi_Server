package com.oyeetaxi.cybergod.futures.tipo_vehiculo.repositories


import com.oyeetaxi.cybergod.futures.tipo_vehiculo.models.TipoVehiculo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TipoVehiculoRepository: MongoRepository<TipoVehiculo, String> {

    @Query("{'seleccionable': ?0}")
    fun findAvailableVehiclesType(seleccionable: Boolean = true) : List<TipoVehiculo>


}