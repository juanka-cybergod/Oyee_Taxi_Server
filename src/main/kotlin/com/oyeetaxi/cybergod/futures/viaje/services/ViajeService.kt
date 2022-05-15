package com.oyeetaxi.cybergod.futures.viaje.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.viaje.interfaces.ViajeInterface
import com.oyeetaxi.cybergod.futures.viaje.models.Viaje
import com.oyeetaxi.cybergod.futures.viaje.repositories.ViajeRepository
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