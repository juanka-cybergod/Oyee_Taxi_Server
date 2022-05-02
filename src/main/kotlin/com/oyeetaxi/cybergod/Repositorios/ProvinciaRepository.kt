package com.oyeetaxi.cybergod.Repositorios


import com.oyeetaxi.cybergod.Modelos.Provincia
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProvinciaRepository: MongoRepository<Provincia, String> {

}