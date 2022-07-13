package com.oyeetaxi.cybergod.futures.actualizacion.repositories


import com.oyeetaxi.cybergod.futures.actualizacion.models.Actualizacion
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ActualizacionRepository: MongoRepository<Actualizacion, String> {

    @Query("{'version': { \$gt : ?0 , \$lte : ?1   }}")
    fun findUpgradableAppUpdateListBetweenVersion(fromVersion: Int = 1, toVersion: Int, sort: Sort = Sort.by(Sort.Direction.DESC, "version")) : List<Actualizacion>

}