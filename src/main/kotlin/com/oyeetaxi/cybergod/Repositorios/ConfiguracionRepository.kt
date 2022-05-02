package com.oyeetaxi.cybergod.Repositorios


import com.oyeetaxi.cybergod.Modelos.Configuracion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfiguracionRepository: MongoRepository<Configuracion, String> {

}