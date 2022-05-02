package com.oyeetaxi.cybergod.Repositorios


import com.oyeetaxi.cybergod.Modelos.Viaje
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ViajeRepository: MongoRepository<Viaje, String> {

}