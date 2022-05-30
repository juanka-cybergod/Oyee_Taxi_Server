package com.oyeetaxi.cybergod.futures.configuracion.controller


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.UpdateConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_CONFIGURACION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_CONFIGURACION)
class ConfiguracionRestController {

    @Autowired
    val configuracionBusiness : ConfiguracionService? = null



    @GetMapping("/getUpdateConfiguration")
    fun getUpdateConfiguration():ResponseEntity<UpdateConfiguracion>{
        return try {
            ResponseEntity(configuracionBusiness!!.getUpdateConfiguration(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getTwilioBalance")
    fun getTwilioBalance():ResponseEntity<Any>{
        return try {
            ResponseEntity(configuracionBusiness!!.getTwilioBalance(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getSmsProvider")
    fun getSmsProvider():ResponseEntity<SmsProvider>{
        return try {
            ResponseEntity(configuracionBusiness!!.getSmsProvider(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/isServerActive")
    fun isServerActive():ResponseEntity<Boolean>{
        return try {
            ResponseEntity(configuracionBusiness!!.isServerActive(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/isServerActiveForAdmin")
    fun isServerActiveForAdmin():ResponseEntity<Boolean>{
        return try {
            ResponseEntity(configuracionBusiness!!.isServerActiveForAdmin(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getRemaningSMS")
    fun getRemaningSMS(): ResponseEntity<Int>{
        return try {
            ResponseEntity( configuracionBusiness!!.getRemaningSMS(),HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getConfiguration")
    fun getConfiguration():ResponseEntity<Configuracion> {
        return try {
            ResponseEntity(configuracionBusiness!!.getConfiguration(),HttpStatus.OK)
        }catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



    @PutMapping("/updateConfiguration")
    fun updateConfiguration(@RequestBody configuracion: Configuracion): ResponseEntity<Configuracion>{
        return try {
            ResponseEntity(configuracionBusiness!!.updateConfiguration(configuracion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }




    @PutMapping("/setTwilioConfiguration")
    fun setTwilioConfiguration(@RequestBody twilioConfiguracion: TwilioConfiguracion): ResponseEntity<Boolean>{
        return try {
            configuracionBusiness!!.setTwilioConfiguration(twilioConfiguracion)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



}