package com.oyeetaxi.cybergod.futures.valoracion.controller



import com.oyeetaxi.cybergod.futures.base.controller.BaseRestController
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.valoracion.models.Valoracion
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_VALORACION

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_VALORACION)
class ValoracionRestController: BaseRestController() {


    @PostMapping("/addUpdateValoration")
    fun addUpdateValoration(@RequestBody valoracion: Valoracion): ResponseEntity<Any>{

        return try {
            ResponseEntity(valoracionBusiness!!.addUpdateValoration(valoracion),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }


    }

    @GetMapping("/getValorationAverageByUserId={id}")
    fun getValorationAverageByUserId(@PathVariable("id") id:String): ResponseEntity<Any> {

        val valoracionAverage = valoracionBusiness!!.getValorationAverageByUserId(id)

        return if (valoracionAverage != null) {
            ResponseEntity(valoracionAverage,HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("/getValorationByUsersId")
    fun getValorationByUsersId(@RequestParam("idUsuarioValora") idUsuarioValora:String, @RequestParam("idUsuarioValorado") idUsuarioValorado:String): ResponseEntity<Any> {

        return try {
            ResponseEntity(valoracionBusiness!!.getValorationByUsersId(idUsuarioValora, idUsuarioValorado),HttpStatus.OK)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }





}