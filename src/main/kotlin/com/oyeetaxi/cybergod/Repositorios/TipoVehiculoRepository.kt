package com.oyeetaxi.cybergod.Repositorios


import com.oyeetaxi.cybergod.Modelos.TipoVehiculo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TipoVehiculoRepository: MongoRepository<TipoVehiculo, String> {

}