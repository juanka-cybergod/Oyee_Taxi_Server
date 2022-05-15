package com.oyeetaxi.cybergod.futures.provincia.repositories


import com.oyeetaxi.cybergod.futures.provincia.models.Provincia
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProvinciaRepository: MongoRepository<Provincia, String> {

    @Query("{'visible': ?0}")
    fun findAvailableProvinces(visible: Boolean = true) : List<Provincia>

}