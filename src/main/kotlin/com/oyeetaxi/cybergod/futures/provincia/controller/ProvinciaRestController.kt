package com.oyeetaxi.cybergod.futures.provincia.controller




import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.provincia.models.Provincia
import com.oyeetaxi.cybergod.futures.provincia.services.ProvinciaService
import com.oyeetaxi.cybergod.utils.Constants
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_PROVINCIAS

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_PROVINCIAS)
class ProvinciaRestController {

    @Autowired
    val provinciasBusiness : ProvinciaService? = null



    @GetMapping("/getAllProvinces")
    fun getAllProvinces():ResponseEntity<List<Provincia>>{
        return try {
            ResponseEntity(provinciasBusiness!!.getAllProvinces(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getAvailableProvinces")
    fun getAvailableProvinces():ResponseEntity<List<Provincia>>{
        return try {
            ResponseEntity(provinciasBusiness!!.getAvailableProvinces(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getProvinceById={id}")
    fun getProvinceById(@PathVariable("id") idProvincia: String  ):ResponseEntity<Provincia> {
        return try {
            ResponseEntity(provinciasBusiness!!.getProvinceById(idProvincia),HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PostMapping("/addProvince")
    fun addProvince(@RequestBody provincia: Provincia): ResponseEntity<Any>{
        return try {
//            provinciasBusiness!!.addProvince(provincia)
//            val responseHeader = org.springframework.http.HttpHeaders()
//            responseHeader.set("location",Constants.URL_BASE_PROVINCIAS + "/" + provincia.nombre)
            ResponseEntity(provinciasBusiness!!.addProvince(provincia),HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/updateProvince")
    fun updateProvince(@RequestBody provincia: Provincia): ResponseEntity<Provincia>{
        return try {
            ResponseEntity(provinciasBusiness!!.updateProvince(provincia),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @DeleteMapping("/deleteProvinceById={id}")
    fun deleteProvinceById(@PathVariable("id") idProvincia: String): ResponseEntity<Any>{
        return try {
            provinciasBusiness!!.deleteProvinceById(idProvincia)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @DeleteMapping("/deleteAllProvinces")
    fun deleteAllProvinces(): ResponseEntity<Any>{
        return try {
            provinciasBusiness!!.deleteAllProvinces()

            val responseHeader = org.springframework.http.HttpHeaders()
            responseHeader.set("BORRADOS","SI")
            ResponseEntity(responseHeader,HttpStatus.CREATED)
            //ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/countProvinces")
    fun countProvinces():String{
        return try {
            provinciasBusiness!!.countProvinces().toString()

        }catch (e:Exception){
            "-1"
        }
    }



}