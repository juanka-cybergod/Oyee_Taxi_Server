package com.oyeetaxi.cybergod.Servicios


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Interfaces.ConfiguracionInterface
import com.oyeetaxi.cybergod.Modelos.Configuracion
import com.oyeetaxi.cybergod.Modelos.Config.EmailConfiguracion
import com.oyeetaxi.cybergod.Modelos.SmsProvider
import com.oyeetaxi.cybergod.Modelos.Config.TwilioConfiguracion
import com.oyeetaxi.cybergod.Modelos.Config.UpdateConfiguracion
import com.oyeetaxi.cybergod.Repositorios.ConfiguracionRepository
import com.oyeetaxi.cybergod.Utiles.Constants.DEFAULT_CONFIG

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ConfiguracionService : ConfiguracionInterface {

    @Autowired
    val configuracionRepository: ConfiguracionRepository? = null



    @Throws(BusinessException::class, NotFoundException::class)
    override fun getConfiguration(): Configuracion {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get()
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun setConfiguration(configuracion: Configuracion): Boolean {

        return try {
            configuracionRepository!!.save(configuracion)
            true
        }catch (e:Exception) {
            throw BusinessException(e.message)

        }

    }




    @Throws(BusinessException::class,NotFoundException::class)
    override fun setTwilioConfiguration(twilioConfiguracion: TwilioConfiguracion): Boolean {

        val tempConfiguracion = Configuracion(
            twilioConfiguracion = twilioConfiguracion
        )

        return try {
            configuracionRepository!!.save(tempConfiguracion)
            true
        }catch (e:Exception) {
            throw BusinessException(e.message)

        }

    }




    @Throws(BusinessException::class, NotFoundException::class)
    override fun getTwilioConfiguration(): TwilioConfiguracion {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().twilioConfiguracion!!


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun isServerActive(): Boolean {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().servidorActivoClientes!!


    }





    @Throws(BusinessException::class,NotFoundException::class)
    override fun isServerActiveForAdmin(): Boolean {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().servidorActivoAdministradores!!


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateCredit(): Boolean {

        val tempTwilioConfiguration =getTwilioConfiguration()

        val remaningCredit: Double = tempTwilioConfiguration.remaningCredit!!
        val smsCost: Double = tempTwilioConfiguration.smsCost!!

        val newTwilioConfiguracion = TwilioConfiguracion(
            account_sid = tempTwilioConfiguration.account_sid,
            auth_token = tempTwilioConfiguration.auth_token,
            trial_number = tempTwilioConfiguration.trial_number,
            smsCost = tempTwilioConfiguration.smsCost,
            remaningCredit = remaningCredit - smsCost,
        )

        return setTwilioConfiguration(
            newTwilioConfiguracion
        )


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun getRemaningSMS(): Int {

        val tempTwilioConfiguration =getTwilioConfiguration()

        val remaningCredit: Double = tempTwilioConfiguration.remaningCredit!!
        val smsCost: Double = tempTwilioConfiguration.smsCost!!

        return Math.round( (remaningCredit/smsCost) .toFloat() )
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getSmsProvider(): SmsProvider {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().smsProvider!!


    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getEmailConfiguration(): EmailConfiguracion {
        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().emailConfiguracion!!

    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getUpdateConfiguration(): UpdateConfiguracion {
        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().updateConfiguracion!!

    }


}