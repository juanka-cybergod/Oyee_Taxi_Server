package com.oyeetaxi.cybergod.Servicios


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Interfaces.ViajeInterface
import com.oyeetaxi.cybergod.Modelos.Viaje
import com.oyeetaxi.cybergod.Repositorios.ViajeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ViajeService : ViajeInterface {

    @Autowired
    val viajeRepository: ViajeRepository? = null



    @Throws(BusinessException::class,NotFoundException::class)
    override fun addViaje(viaje: Viaje): Viaje {

        try {
            return viajeRepository!!.insert(viaje)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

    }




}