package com.oyeetaxi.cybergod.futures.configuracion.repositories


import com.oyeetaxi.cybergod.futures.configuracion.models.Actualizacion
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ActualizacionRepository: MongoRepository<Actualizacion, String> {

   // @Query("{'version': {\$regex: gt,\$options: 'i' } }")
//    @Query("{'version': { \$gte : ?0 }}")
//    @Query("{'version': { \$gt : ?0 }}")
    @Query("{'version': { \$gt : ?0 , \$lte : ?1   }}")
    fun findUpgradableAppUpdateListBetweenVersion(fromVersion: Int = 1, toVersion: Int, sort: Sort = Sort.by(Sort.Direction.DESC, "version")) : List<Actualizacion>

}