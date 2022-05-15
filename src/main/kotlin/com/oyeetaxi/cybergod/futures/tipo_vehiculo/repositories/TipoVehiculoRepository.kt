package com.oyeetaxi.cybergod.futures.tipo_vehiculo.repositories


import com.oyeetaxi.cybergod.futures.tipo_vehiculo.models.TipoVehiculo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TipoVehiculoRepository: MongoRepository<TipoVehiculo, String> {

}