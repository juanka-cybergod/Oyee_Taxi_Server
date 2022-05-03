package com.oyeetaxi.cybergod.Controladores


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Modelos.Configuracion
import com.oyeetaxi.cybergod.Modelos.SmsProvider
import com.oyeetaxi.cybergod.Modelos.Config.TwilioConfiguracion
import com.oyeetaxi.cybergod.Modelos.Config.UpdateConfiguracion
import com.oyeetaxi.cybergod.Servicios.ConfiguracionService
import com.oyeetaxi.cybergod.Utiles.Constants.URL_BASE_CONFIGURACION
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