package com.oyeetaxi.cybergod.futures.configuracion.controller


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.ForbiddenException
import com.oyeetaxi.cybergod.futures.configuracion.models.Actualizacion
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.futures.share.interfaces.SmsInterface
import com.oyeetaxi.cybergod.futures.share.services.SmsTwilioService
import com.oyeetaxi.cybergod.utils.Constants.ERROR_RESPONSE
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





    //APP_UPDATE

    @GetMapping("/getAppUpdate")
    fun getAppUpdate(@RequestParam("clientAppVersion") clientAppVersion: Int):ResponseEntity<Actualizacion>{
        val responseHeader = org.springframework.http.HttpHeaders()

        return try {
            ResponseEntity(configuracionService.getAppUpdate(clientAppVersion),HttpStatus.OK)
        }catch (e:BusinessException){
            responseHeader.set(ERROR_RESPONSE,e.message)
            ResponseEntity(responseHeader,HttpStatus.NOT_FOUND)
        }

        // ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/getAllAppUpdates")
    fun getAllAppUpdates():ResponseEntity<List<Actualizacion>>{
        return try {
            ResponseEntity(configuracionService.getAllAppUpdates().toList(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/setAppUpdateActiveById")
    fun setAppUpdateActiveById(@RequestParam("idActualizacion") idActualizacion: String,@RequestParam("active") active: Boolean):ResponseEntity<Boolean>{
        return try {
            ResponseEntity(configuracionService.setAppUpdateActiveById(idActualizacion,active),HttpStatus.OK)
        }catch (e:Exception){
            //println(e.message.toString())
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }


    @PostMapping("/addAppUpdate")
    fun addAppUpdate(@RequestBody actualizacion: Actualizacion): ResponseEntity<Any>{
        return try {
            ResponseEntity( configuracionService.addAppUpdate(actualizacion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/editAppUpdate")
    fun editAppUpdate(@RequestBody actualizacion: Actualizacion): ResponseEntity<Any>{
        return try {
            ResponseEntity( configuracionService.editAppUpdate(actualizacion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/deleteAppUpdateById")
    fun deleteAppUpdateById(@RequestParam("idActualizacion") idActualizacion: String): ResponseEntity<Any>{
        return try {
            ResponseEntity( configuracionService.deleteAppUpdateById(idActualizacion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }





}