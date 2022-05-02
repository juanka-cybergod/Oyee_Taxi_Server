package com.oyeetaxi.cybergod.Controladores

import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Modelos.SmsRequest
import com.oyeetaxi.cybergod.Servicios.ConfiguracionService
import com.oyeetaxi.cybergod.Servicios.SmsServicio
import com.oyeetaxi.cybergod.Utiles.Constants.URL_BASE_SMS
import com.oyeetaxi.cybergod.Utiles.Utils.generateOTP
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_SMS)
class SmsController {


    @Autowired
    val smsBusiness : SmsServicio? = null

    @Autowired
    val configuracionBusiness : ConfiguracionService? = null


    @GetMapping("/sendSMSTest")
    fun sendSMSTest(@RequestParam("phoneNumber") phoneNumber: String): ResponseEntity<Any> {
        return try {
            val codeOTP = 123456 //smsBusiness!!.generateOTP()
                ResponseEntity(codeOTP, HttpStatus.OK)
            } catch (e: BusinessException) {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
    }




    @GetMapping("/sendSMS")
  //fun sendSMS(@RequestParam("phoneNumber") phoneNumber: String, @RequestParam("message") message: String): ResponseEntity<Any> {
    fun sendSMS(@RequestParam("phoneNumber") phoneNumber: String): ResponseEntity<Any> {
        return try {

            val codeOTP = generateOTP()
            val message = "El Código de Verificación para ud es $codeOTP"

            val smsRequest = SmsRequest(phoneNumber, message)

            if (smsBusiness!!.sendSMS(smsRequest)) {
                configuracionBusiness!!.updateCredit()
                ResponseEntity(codeOTP, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }



        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }






//      TODO METODO SICRONO PERO PUEDE DEMORAR EL SERVIDOR ??
//    @PostMapping("/sendSMS")
//    fun sendSMS(@RequestParam("phoneNumber") phoneNumber: String, @RequestParam("message") message: String): ResponseEntity<Any> {
//
//        var responseEntity: ResponseEntity <Any>
//
//        val smsRequest = SmsRequest(phoneNumber, message)
//
//        if (smsBusiness!!.sendSMS(smsRequest)) {
//            val responseHeader = org.springframework.http.HttpHeaders()
//            responseHeader.set("verificationCode", "6 Digits Code")
//            responseEntity = ResponseEntity(responseHeader, HttpStatus.OK)
//        } else {
//            responseEntity = ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
//        }
//
//
//        return responseEntity
//
//    }









}