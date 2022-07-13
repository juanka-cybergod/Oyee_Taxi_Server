package com.oyeetaxi.cybergod.futures.configuracion.controller


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.futures.share.interfaces.SmsInterface
import com.oyeetaxi.cybergod.futures.share.services.SmsTwilioService
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_CONFIGURACION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_CONFIGURACION)
class ConfiguracionRestController(
    @Autowired private val configuracionService : ConfiguracionService,
    @Autowired private val smsTwilioService : SmsTwilioService
) {


    @GetMapping("/getSMSBalance")
    fun getSMSBalance():ResponseEntity<Any>{
        val smsProvider :SmsInterface? =
            when (configuracionService.getSmsProvider()) {
            SmsProvider.TWILIO -> {smsTwilioService}
            else -> null
            }

        return try {
            ResponseEntity(smsProvider?.getBalance(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getSmsProvider")
    fun getSmsProvider():ResponseEntity<SmsProvider>{
        return try {
            ResponseEntity(configuracionService.getSmsProvider(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/isServerActive")
    fun isServerActive():ResponseEntity<Boolean>{
        return try {
            ResponseEntity(configuracionService.isServerActive(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/isServerActiveForAdmin")
    fun isServerActiveForAdmin():ResponseEntity<Boolean>{
        return try {
            ResponseEntity(configuracionService.isServerActiveForAdmin(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getRemaningSMS")
    fun getRemaningSMS(): ResponseEntity<Int>{


        val smsProvider :SmsInterface? =
            when (configuracionService.getSmsProvider()) {
                SmsProvider.TWILIO -> {smsTwilioService}
                else -> null
            }

        return try {
            ResponseEntity(smsProvider?.getRemainingSMS(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }


    @GetMapping("/getConfiguration")
    fun getConfiguration():ResponseEntity<Configuracion> {
        return try {
            ResponseEntity(configuracionService.getConfiguration(),HttpStatus.OK)
        }catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



    @PutMapping("/updateConfiguration")
    fun updateConfiguration(@RequestBody configuracion: Configuracion): ResponseEntity<Configuracion>{
        return try {
            ResponseEntity(configuracionService.updateConfiguration(configuracion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }





}