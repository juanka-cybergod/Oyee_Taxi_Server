package com.oyeetaxi.cybergod.futures.configuracion.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.configuracion.interfaces.ConfiguracionInterface
import com.oyeetaxi.cybergod.futures.configuracion.models.Actualizacion
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.repositories.ActualizacionRepository
import com.oyeetaxi.cybergod.futures.configuracion.repositories.ConfiguracionRepository
import com.oyeetaxi.cybergod.futures.fichero.services.FicheroServicio
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class ConfiguracionService(
    @Autowired private val configuracionRepository: ConfiguracionRepository,
    @Autowired private val actualizacionRepository: ActualizacionRepository,
    @Autowired private val ficheroServicio: FicheroServicio,
) : ConfiguracionInterface {


    @Throws(BusinessException::class, NotFoundException::class)
    override fun getConfiguration(): Configuracion {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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


            configuracion.actualizacionHabilita?.let { configuracionModificar.actualizacionHabilita = it }

            configuracion.socialConfiguracion?.let { socialConfiguracion ->
                socialConfiguracion.disponible?.let { configuracionModificar.socialConfiguracion?.disponible = it }
                socialConfiguracion.phone?.let { configuracionModificar.socialConfiguracion?.phone = it }
                socialConfiguracion.email?.let { configuracionModificar.socialConfiguracion?.email = it }
                socialConfiguracion.web?.let { configuracionModificar.socialConfiguracion?.web = it }
                socialConfiguracion.redesSociales?.let { configuracionModificar.socialConfiguracion?.redesSociales = it }
            }


            try {
                configuracionActualizada = configuracionRepository.save(configuracionModificar)

            }catch (e:Exception){

                throw BusinessException(e.message)

            }


        }


        return configuracionActualizada

    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getTwilioConfiguration(): TwilioConfiguracion {

        val optional: Optional<Configuracion>

        try {
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
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
            optional = configuracionRepository.findById(DEFAULT_CONFIG)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Configuracion con ID $DEFAULT_CONFIG No Encontrado")
        }
        return optional.get().emailConfiguracion!!

    }



    //APP_UPDATE


    @Throws(Exception::class)
    override fun getAppUpdate(clientAppVersion: Int): Actualizacion {

        val actualizacionHabilitada = try {
            configuracionRepository.findById(DEFAULT_CONFIG).get().actualizacionHabilita
        }catch (e:Exception) {
            throw BusinessException("Las Actualizaciones no se han configurado en el Servidor")
        }

        if (actualizacionHabilitada!=true) {
            throw BusinessException("Las actualizaciones están temporalmente deshabilitadas")
        }

        val allAppUpdateList =  actualizacionRepository.findAll()
        if (allAppUpdateList.isEmpty()) {
            throw BusinessException("No existen actualizaciones disponibles")
        }

        val lastAppUpdate = allAppUpdateList.findLast { actualizacion -> actualizacion.active == true } ?: allAppUpdateList.last()
        val toVersion = lastAppUpdate?.version ?: clientAppVersion


        val upgradableAppUpdateList =  actualizacionRepository.findUpgradableAppUpdateListBetweenVersion(clientAppVersion, toVersion)
        if (upgradableAppUpdateList.isEmpty()) {
            throw BusinessException("La aplicación está actualizada")
        }

        //Obtener las lista de Cambios de todas las versiones que falta por actualizar
        val allChangeLog : MutableList<String> = ArrayList()
        upgradableAppUpdateList.forEach { actualizacion ->
            actualizacion.versionString?.let { allChangeLog.add("Cambios en v$it") }
            actualizacion.description?.let { allChangeLog.addAll(it) }
            println(actualizacion.versionString)
        }
        lastAppUpdate.description = allChangeLog

        //Si falto alguna actualizacion requerida intermedia dese la actual del cliente hasta la ultima aplicable tambien esa ultima debe ser requerida
        lastAppUpdate.forceUpdate = upgradableAppUpdateList.find { actualizacion -> actualizacion.forceUpdate==true}?.forceUpdate ?:false


        //Obtener el Tamaño real en MB de esa Actualizacion si Existe y enviar Actualizacion correcta al usuario
        return try {

            lastAppUpdate.fileSize = ficheroServicio.getFileSize(lastAppUpdate.appURL)
            lastAppUpdate

        } catch (e :NotFoundException) {
            throw BusinessException("No disponible por el momento")
        }


    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun addAppUpdate(actualizacion: Actualizacion): Actualizacion {
        try {
            return actualizacionRepository.insert(actualizacion)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun editAppUpdate(actualizacion: Actualizacion): Actualizacion {


        val optional:Optional<Actualizacion>
        var actualizacionActualizada : Actualizacion = actualizacion

        actualizacion.id?.let { id ->

            try {
                optional = actualizacionRepository.findById(id)
            }catch (e:Exception) {
                throw BusinessException(e.message)
            }

            if (!optional.isPresent){
                throw NotFoundException("Actualizacion con ID $id No Encontrada")
            } else {

                val actualizacionModificar : Actualizacion = optional.get()

                actualizacion.active?.let { actualizacionModificar.active = it}
                actualizacion.version?.let { actualizacionModificar.version = it}
                actualizacion.versionString?.let { actualizacionModificar.versionString = it}
                actualizacion.fileSize?.let { actualizacionModificar.fileSize = it}
                actualizacion.appURL?.let { actualizacionModificar.appURL = it}
                actualizacion.playStorePackageName?.let { actualizacionModificar.playStorePackageName = it}
                actualizacion.forceUpdate?.let { actualizacionModificar.forceUpdate = it}
                actualizacion.description?.let { actualizacionModificar.description = it}


                try {
                    actualizacionActualizada = actualizacionRepository.save(actualizacionModificar)
                }catch (e:Exception){
                    throw BusinessException(e.message)

                }

            }

        }

        return actualizacionActualizada
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAppUpdateById(idActualizacion: String): Boolean {

        val optional:Optional<Actualizacion>

        try {
            optional = actualizacionRepository.findById(idActualizacion)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Actualizacion con ID $idActualizacion No Encontrada")
        } else {

            return try {
                actualizacionRepository.deleteById(idActualizacion)
                true
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }

    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun getAllAppUpdates():List<Actualizacion>{
        try {
            return actualizacionRepository.findAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun setAppUpdateActiveById(idActualizacion: String, active:Boolean) :Boolean {

        var success = false

        actualizacionRepository.findAll().forEach { actualizacion ->

            if (actualizacion.id.equals(idActualizacion,true)) {
                success = try {
                    editAppUpdate(
                        Actualizacion(
                            id = actualizacion.id,
                            active = active
                        )
                    )
                    true
                } catch (e:Exception) {
                    throw BusinessException(e.message)
                }


            } else {
                editAppUpdate(
                    Actualizacion(
                        id = actualizacion.id,
                        active = false
                    )
                )
            }

        }


        return success

    }



}