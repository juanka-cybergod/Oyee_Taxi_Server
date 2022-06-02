package com.oyeetaxi.cybergod.futures.share.services

import com.oyeetaxi.cybergod.configuration.CoroutineConfiguration
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.share.interfaces.SmsInterface
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.utils.Constants
import com.oyeetaxi.cybergod.utils.Constants.TWILIO_BASE_URL
import com.oyeetaxi.cybergod.utils.Utils.getBalanceFromXMLResponse
import com.oyeetaxi.cybergod.utils.Utils.getEncodedAuthorization
import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import io.netty.handler.timeout.TimeoutException
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.time.Duration
import kotlin.math.roundToInt


@Service
class SmsTwilioService(
    @Autowired private val scope: CoroutineConfiguration,
    @Autowired val configurationBusiness : ConfiguracionService,
    @Autowired val webClient: WebClient
    ): SmsInterface  {

    private var logger = LoggerFactory.getLogger(SmsTwilioService::class.java)


    private fun getCurrentConfigurationInstance():TwilioConfiguracion{
        return configurationBusiness.getTwilioConfiguration()
    }

    override fun sendSMS(phoneNumber:String, message:String):Boolean {

        val twilioConfiguration: TwilioConfiguracion = getCurrentConfigurationInstance()

        Twilio.init(
            twilioConfiguration.account_sid,
            twilioConfiguration.auth_token
        )

        val messageCreator = Message.creator(
            PhoneNumber(phoneNumber), //TO
            PhoneNumber(twilioConfiguration.trial_number), //FROM
            message, //BODY
        )

        var smsSentOK = false
        smsSentOK = try {
            messageCreator.create()
           true
        }

        catch (e: Exception){
            logger.info("Fail To sendSMS with Twilio Service Exception = $e")
           false
        }


        if (smsSentOK) {
            scope.launch {
                getBalance()
            }
        }

        return smsSentOK

    }





    @Throws(BusinessException::class)
    override fun getBalance(): Balance?{

        println("Getting Twilio Balance ...")

        val twilioConfiguration: TwilioConfiguracion = getCurrentConfigurationInstance()

        getCurrentConfigurationInstance()
        val apiURL = "${TWILIO_BASE_URL}${twilioConfiguration.account_sid}/Balance.json%20-u%20${twilioConfiguration.account_sid}:${twilioConfiguration.auth_token}"

        val response: ResponseEntity<String>? = try {
            webClient
                .get()
                .uri(apiURL)
                .header(Constants.AUTHORIZATION, getEncodedAuthorization(twilioConfiguration.account_sid.orEmpty(), twilioConfiguration.auth_token.orEmpty()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String::class.java)
                .timeout(Duration.ofSeconds(40))  // timeout
                .block()

        } catch (e: WebClientResponseException) {
            val exceptionString = "Fail to getTwilioBalance WebClientResponseException = " +
                    when (e.rawStatusCode ) {
                        451 -> {"Unavailable For Legal Reasons Set VPN Connection"}
                        else -> {e.message}
                    }
            println(exceptionString)
            throw BusinessException(exceptionString)
        } catch (e: TimeoutException) {
            val exceptionString = "Fail to getTwilioBalance TimeoutException = ${e.message}"
            println(exceptionString)
            throw BusinessException(exceptionString)
        }catch (e:Exception) {
            println("Fail to getTwilioBalance Exception = $e")
            throw BusinessException("")
        }


        println("Twilio Response = ${response.toString()}")


        return when (response?.statusCodeValue) {
            HttpStatus.OK.value() -> {

                val balance = response.body?.getBalanceFromXMLResponse()
                balance?.let {
                    saveBalance(newBalance = it)
                }
                balance
            }
            else -> {throw BusinessException("Fail to getTwilioBalance")}
        }


    }


    private fun saveBalance(newBalance:Balance, newSmsCost:Double?=null):Boolean{


        val twilioConfiguration : TwilioConfiguracion = getCurrentConfigurationInstance()

        val oldBalanceQuantity: Double? = twilioConfiguration.balance?.balance
        val newBalanceQuantity: Double? = newBalance.balance


        val smsCost = if (newBalanceQuantity!=null && oldBalanceQuantity!=null && newBalanceQuantity < oldBalanceQuantity) {
            oldBalanceQuantity - newBalanceQuantity
        } else {
            twilioConfiguration.smsCost!!
        }



        return try{
            configurationBusiness.updateConfiguration(
                Configuracion(
                    twilioConfiguracion = TwilioConfiguracion(
                        smsCost = newSmsCost?: smsCost, //RECALCULAR
                        balance = newBalance

                    )
                )

            )
            true

        } catch (e:Exception) {
            throw BusinessException("Fail to save Twilio Credit Exception : $e")
        }

    }



    @Throws(BusinessException::class, NotFoundException::class)
    override fun getRemainingSMS(): Int {

        val twilioConfiguration: TwilioConfiguracion = getCurrentConfigurationInstance()

        val remainingCredit: Double = twilioConfiguration.balance?.balance!!
        val smsCost: Double = twilioConfiguration.smsCost!!

        return (remainingCredit / smsCost).toFloat().roundToInt()
    }






}
























/**
/** Solo Devuelve Cuando la respuesta es OK
val response = webClient!!.build()
.get()
.uri(uri)
.header(AUTHORIZATION, getEncodedAuthorization(userId,userToken))
.accept(MediaType.APPLICATION_JSON)
.retrieve()
.bodyToMono<String>()
.block()

println(response)
response.toString()

} catch (e:Exception) {
println("Fail to getTwilioBalance Exception = $e")
null
}
*/
 *
 *
// METODO SICRONO PERO PUEDE DEMORAR EL SERVIDOR ??
override fun sendSMS(smsRequest: SmsRequest):Boolean {

val twilioClient = TwilioRestClient.Builder(
account_sid,
auth_token
).build()

Twilio.init(
account_sid,
auth_token
)


val messageCreator = Message.creator(
PhoneNumber(smsRequest.phoneNumber), //TO
PhoneNumber(trial_number), //FROM
smsRequest.message, //BODY
)


return try {
messageCreator.create() //Este Metodo retorna cuando realmente envio el sms y llego al destino pero Demora la Respuesta
// messageCreator.createAsync()
LOGGER.info("Enviado Mensaje OK {$smsRequest} ")
true
} catch (e: Exception) {
LOGGER.info("Error al Enviar Mensaje $e ")
false
}


}
 */