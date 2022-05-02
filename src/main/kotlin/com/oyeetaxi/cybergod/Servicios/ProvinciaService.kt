package com.oyeetaxi.cybergod.Servicios


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Interfaces.ProvinciaInterface
import com.oyeetaxi.cybergod.Modelos.Provincia
import com.oyeetaxi.cybergod.Repositorios.ProvinciaRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProvinciaService : ProvinciaInterface {


    @Autowired
    val provinciaRepository: ProvinciaRepository? = null


    @Throws(BusinessException::class)
    override fun getAllProvinces(): List<Provincia> {
        try {
            return provinciaRepository!!.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getProvinceById(idProvincia: String): Provincia {
        val optional: Optional<Provincia>

        try {
            optional = provinciaRepository!!.findById(idProvincia)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Provincia con ID $idProvincia No Encontrado")
        }
        return optional.get()
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun addProvince(provincia: Provincia): Provincia {
        try {
            return provinciaRepository!!.insert(provincia)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateProvince(provincia: Provincia): Provincia {
        try {
            return provinciaRepository!!.save(provincia)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countProvinces(): Long {
        try {
            return  provinciaRepository!!.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteProvinceById(idProvincia: String) {

        val optional:Optional<Provincia>

        try {
            optional = provinciaRepository!!.findById(idProvincia)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Provincia con ID $idProvincia No Encontrado")
        } else {

            try {
                provinciaRepository!!.deleteById(idProvincia)
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllProvinces() {
        try {
            provinciaRepository!!.deleteAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }








}