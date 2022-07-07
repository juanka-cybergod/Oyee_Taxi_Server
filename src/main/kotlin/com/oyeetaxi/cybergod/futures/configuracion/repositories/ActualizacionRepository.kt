package com.oyeetaxi.cybergod.futures.configuracion.repositories


import com.oyeetaxi.cybergod.futures.configuracion.models.Actualizacion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ActualizacionRepository: MongoRepository<Actualizacion, String> {

}