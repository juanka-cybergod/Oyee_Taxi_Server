package com.oyeetaxi.cybergod.futures.configuracion.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.configuracion.interfaces.ConfiguracionInterface
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.UpdateConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.repositories.ConfiguracionRepository
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
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
    override fun updateConfiguration(configuracion: Configuracion): Configuracion {

        val optional:Optional<Configuracion>
        configuracion.id=DEFAULT_CONFIG

        var configuracionActualizada : Configuracion = configuracion

        try {
            optional = configuracionRepository!!.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Configuracion $DEFAULT_CONFIG No Encontrada")
        } else {

            val configuracionModificar : Configuracion = optional.get()

            configuracion.servidorActivoClientes?.let { configuracionModificar.servidorActivoClientes = it }
            configuracion.servidorActivoAdministradores?.let { configuracionModificar.servidorActivoAdministradores = it }
            configuracion.smsProvider?.let { configuracionModificar.smsProvider = it }
            configuracion.motivoServidorInactivoClientes?.let { configuracionModificar.motivoServidorInactivoClientes = it }
            configuracion.motivoServidorInactivoAdministradores?.let { configuracionModificar.motivoServidorInactivoAdministradores = it }


            configuracion.twilioConfiguracion?.let { twilioConfiguration ->
                twilioConfiguration.account_sid?.let { configuracionModificar.twilioConfiguracion?.account_sid = it }
                twilioConfiguration.auth_token?.let { configuracionModificar.twilioConfiguracion?.auth_token = it }
                twilioConfiguration.trial_number?.let { configuracionModificar.twilioConfiguracion?.trial_number = it }
//                twilioConfiguration.remainingCredit?.let { configuracionModificar.twilioConfiguracion?.remainingCredit = it }
                twilioConfiguration.smsCost?.let { configuracionModificar.twilioConfiguracion?.smsCost = it }
                twilioConfiguration.balance?.let { balance ->
                    balance.balance?.let { configuracionModificar.twilioConfiguracion?.balance?.balance = it }
                    balance.currency?.let { configuracionModificar.twilioConfiguracion?.balance?.currency = it }
                    balance.accountSid?.let { configuracionModificar.twilioConfiguracion?.balance?.accountSid = it }
                }
            }

            configuracion.emailConfiguracion?.let {emailConfiguracion ->
                emailConfiguracion.host?.let { configuracionModificar.emailConfiguracion?.host = it }
                emailConfiguracion.port?.let { configuracionModificar.emailConfiguracion?.port = it }
                emailConfiguracion.username?.let { configuracionModificar.emailConfiguracion?.username = it }
                emailConfiguracion.password?.let { configuracionModificar.emailConfiguracion?.password = it }
                emailConfiguracion.properties_mail_transport_protocol?.let { configuracionModificar.emailConfiguracion?.properties_mail_transport_protocol = it }
                emailConfiguracion.properties_mail_smtp_auth?.let { configuracionModificar.emailConfiguracion?.properties_mail_smtp_auth = it }
                emailConfiguracion.properties_mail_smtp_starttls_enable?.let { configuracionModificar.emailConfiguracion?.properties_mail_smtp_starttls_enable = it }
                emailConfiguracion.properties_mail_debug?.let { configuracionModificar.emailConfiguracion?.properties_mail_debug = it }

            }

            configuracion.updateConfiguracion?.let {updateConfiguracion ->
                updateConfiguracion.available?.let { configuracionModificar.updateConfiguracion?.available = it }
                updateConfiguracion.version?.let { configuracionModificar.updateConfiguracion?.version = it }
                updateConfiguracion.versionString?.let { configuracionModificar.updateConfiguracion?.versionString = it }
                updateConfiguracion.fileSize?.let { configuracionModificar.updateConfiguracion?.fileSize = it }
                updateConfiguracion.appURL?.let { configuracionModificar.updateConfiguracion?.appURL = it }
                updateConfiguracion.packageName?.let { configuracionModificar.updateConfiguracion?.packageName = it }
                updateConfiguracion.forceUpdate?.let { configuracionModificar.updateConfiguracion?.forceUpdate = it }
                updateConfiguracion.description?.let { configuracionModificar.updateConfiguracion?.description = it }

            }

            configuracion.socialConfiguracion?.let { socialConfiguracion ->
                socialConfiguracion.disponible?.let { configuracionModificar.socialConfiguracion?.disponible = it }
                socialConfiguracion.phone?.let { configuracionModificar.socialConfiguracion?.phone = it }
                socialConfiguracion.email?.let { configuracionModificar.socialConfiguracion?.email = it }
                socialConfiguracion.web?.let { configuracionModificar.socialConfiguracion?.web = it }
                socialConfiguracion.redesSociales?.let { configuracionModificar.socialConfiguracion?.redesSociales = it }
            }


            try {
                configuracionActualizada = configuracionRepository!!.save(configuracionModificar)

            }catch (e:Exception){

                throw BusinessException(e.message)

            }


        }


        return configuracionActualizada



//        return try {
//            configuracionRepository!!.save(configuracion)
//            true
//        }catch (e:Exception) {
//            throw BusinessException(e.message)
//
//        }

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