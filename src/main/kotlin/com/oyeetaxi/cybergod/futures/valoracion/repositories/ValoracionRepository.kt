package com.oyeetaxi.cybergod.futures.valoracion.repositories


import com.oyeetaxi.cybergod.futures.valoracion.models.Valoracion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ValoracionRepository: MongoRepository<Valoracion, String> {

    @Query("{'idUsuarioValora': ?0, 'idUsuarioValorado': ?1}")
    fun findValoracionByUsersId(idUsuarioValora: String,idUsuarioValorado: String) : Optional<List<Valoracion>>

    @Query("{'idUsuarioValorado': ?0}")
    fun findValoracionesByUserId(idUsuarioValorado: String) : Optional<List<Valoracion>>


}