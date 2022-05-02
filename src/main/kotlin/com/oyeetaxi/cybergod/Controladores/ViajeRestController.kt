package com.oyeetaxi.cybergod.Controladores




import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Modelos.Viaje
import com.oyeetaxi.cybergod.Servicios.ViajeService
import com.oyeetaxi.cybergod.Utiles.Constants.URL_BASE_VIAJES

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