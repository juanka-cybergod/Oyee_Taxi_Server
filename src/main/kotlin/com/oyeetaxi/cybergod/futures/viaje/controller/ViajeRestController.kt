package com.oyeetaxi.cybergod.futures.viaje.controller




import com.oyeetaxi.cybergod.futures.share.controller.BaseRestController
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.futures.viaje.models.Viaje
import com.oyeetaxi.cybergod.futures.viaje.services.ViajeService
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_VIAJES

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_VIAJES)
class ViajeRestController: BaseRestController() {

    @Autowired
    val viajesBusiness : ViajeService? = null


    @PostMapping("/addViaje")
    fun addProvince(@RequestBody viaje: Viaje): ResponseEntity<Any>{

        return try {
            ResponseEntity( viajesBusiness!!.addViaje(viaje) , HttpStatus.CREATED)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }





}