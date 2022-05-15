package com.oyeetaxi.cybergod.futures.configuracion.repositories


import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfiguracionRepository: MongoRepository<Configuracion, String> {

}