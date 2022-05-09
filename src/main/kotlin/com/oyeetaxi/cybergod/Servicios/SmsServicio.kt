package com.oyeetaxi.cybergod.Servicios

import com.oyeetaxi.cybergod.Interfaces.SmsInterface
import com.oyeetaxi.cybergod.Modelos.SmsRequest
import com.twilio.Twilio
import com.twilio.http.HttpMethod
import com.twilio.http.Request
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SmsServicio: SmsInterface {

    private lateinit var  account_sid: String
    private lateinit var  auth_token: String
    private lateinit var trial_number:String

    @Autowired
    val configuracionBusiness : ConfiguracionService? = null

    private fun setupAuthCredentials(){
        configuracionBusiness!!.getTwilioConfiguration().also {
            account_sid = it.account_sid!!
            auth_token = it.auth_token!!
            trial_number = it.trial_number!!
        }
    }


    private var logger = LoggerFactory.getLogger(SmsServicio::class.java)

    override fun sendSMS(smsRequest: SmsRequest):Boolean {


        setupAuthCredentials()

        Twilio.init(
            account_sid,
            auth_token
        )

//
//        val request=Request(HttpMethod.GET,"")
//        val cliente = Twilio.getRestClient().httpClient.makeRequest(Request(HttpMethod.GET,""))
//        cliente.

        val messageCreator = Message.creator(
            PhoneNumber(smsRequest.phoneNumber), //TO
            PhoneNumber(trial_number), //FROM
            smsRequest.message, //BODY
        )



       return try {
            messageCreator.create()

           true
        } catch (e: Exception){
            logger.info("Error de conexion con el Servidor de Twilio usar VPN en el Servidor")
           false
        }



    }


//      // METODO SICRONO PERO PUEDE DEMORAR EL SERVIDOR ??
//    override fun sendSMS(smsRequest: SmsRequest):Boolean {
//
//        val twilioClient = TwilioRestClient.Builder(
//            account_sid,
//            auth_token
//        ).build()
//
//        Twilio.init(
//            account_sid,
//            auth_token
//        )
//
//
//        val messageCreator = Message.creator(
//            PhoneNumber(smsRequest.phoneNumber), //TO
//            PhoneNumber(trial_number), //FROM
//            smsRequest.message, //BODY
//        )
//
//
//        return try {
//            messageCreator.create() //Este Metodo retorna cuando realmente envio el sms y llego al destino pero Demora la Respuesta
//            // messageCreator.createAsync()
//            LOGGER.info("Enviado Mensaje OK {$smsRequest} ")
//            true
//        } catch (e: Exception) {
//            LOGGER.info("Error al Enviar Mensaje $e ")
//            false
//        }
//
//
//    }

}