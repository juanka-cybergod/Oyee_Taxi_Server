package com.oyeetaxi.cybergod.futures.viaje.repositories


import com.oyeetaxi.cybergod.futures.viaje.models.Viaje
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ViajeRepository: MongoRepository<Viaje, String> {

}