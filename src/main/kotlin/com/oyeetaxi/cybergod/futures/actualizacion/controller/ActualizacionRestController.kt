package com.oyeetaxi.cybergod.futures.actualizacion.controller


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.actualizacion.models.Actualizacion
import com.oyeetaxi.cybergod.futures.actualizacion.services.ActualizacionService
import com.oyeetaxi.cybergod.utils.Constants.ERROR_RESPONSE
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_ACTUALIZACION
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_ACTUALIZACION)
class ActualizacionRestController(
    @Autowired private val actualizacionService: ActualizacionService
) {



    @GetMapping("/getAppUpdate")
    fun getAppUpdate(@RequestParam("clientAppVersion") clientAppVersion: Int):ResponseEntity<Actualizacion>{
        val responseHeader = org.springframework.http.HttpHeaders()

        return try {
            ResponseEntity(actualizacionService.getAppUpdate(clientAppVersion),HttpStatus.OK)
        }catch (e:BusinessException){
            responseHeader.set(ERROR_RESPONSE,e.message)
            ResponseEntity(responseHeader,HttpStatus.NOT_FOUND)
        }

        // ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/getAllAppUpdates")
    fun getAllAppUpdates():ResponseEntity<List<Actualizacion>>{
        return try {
            ResponseEntity(actualizacionService.getAllAppUpdates().toList(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/setAppUpdateActiveById")
    fun setAppUpdateActiveById(@RequestParam("idActualizacion") idActualizacion: String,@RequestParam("active") active: Boolean):ResponseEntity<Boolean>{
        return try {
            ResponseEntity(actualizacionService.setAppUpdateActiveById(idActualizacion,active),HttpStatus.OK)
        }catch (e:Exception){
            //println(e.message.toString())
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }


    @PostMapping("/addAppUpdate")
    fun addAppUpdate(@RequestBody actualizacion: Actualizacion): ResponseEntity<Any>{
        return try {
            ResponseEntity( actualizacionService.addAppUpdate(actualizacion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/editAppUpdate")
    fun editAppUpdate(@RequestBody actualizacion: Actualizacion): ResponseEntity<Any>{
        return try {
            ResponseEntity( actualizacionService.editAppUpdate(actualizacion),HttpStatus.OK)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/deleteAppUpdateById")
    fun deleteAppUpdateById(@RequestParam("idActualizacion") idActualizacion: String): ResponseEntity<Any>{
        return try {
            ResponseEntity( actualizacionService.deleteAppUpdateById(idActualizacion),HttpStatus.OK)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }





}