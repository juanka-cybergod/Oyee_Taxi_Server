package com.oyeetaxi.cybergod.futures.actualizacion.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.actualizacion.interfaces.ActualizacionInterface
import com.oyeetaxi.cybergod.futures.actualizacion.models.Actualizacion
import com.oyeetaxi.cybergod.futures.actualizacion.repositories.ActualizacionRepository
import com.oyeetaxi.cybergod.futures.configuracion.repositories.ConfiguracionRepository
import com.oyeetaxi.cybergod.futures.fichero.services.FicheroServicio
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ActualizacionService(
    @Autowired private val configuracionRepository: ConfiguracionRepository,
    @Autowired private val actualizacionRepository: ActualizacionRepository,
    @Autowired private val ficheroService: FicheroServicio,
) : ActualizacionInterface {


    @Throws(Exception::class)
    override fun getAppUpdate(clientAppVersion: Int): Actualizacion {

        val actualizacionHabilitada = try {
            configuracionRepository.findById(DEFAULT_CONFIG).get().actualizacionHabilita
        }catch (e:Exception) {
            throw BusinessException("Actualizaciones no configuradas")
        }

        if (actualizacionHabilitada!=true) {
            throw BusinessException("Actualizaciones suspendidas")
        }

        val allAppUpdateList =  actualizacionRepository.findAll().sortedBy { it.version }
        if (allAppUpdateList.isEmpty()) {
            throw BusinessException("No hay actualizaciones disponibles")
        }

        val lastAppUpdate = allAppUpdateList.findLast { actualizacion -> actualizacion.active == true } ?: allAppUpdateList.last()
        val toVersion = lastAppUpdate?.version ?: clientAppVersion


        val upgradableAppUpdateList =  actualizacionRepository.findUpgradableAppUpdateListBetweenVersion(clientAppVersion, toVersion)
        if (upgradableAppUpdateList.isEmpty()) {
            throw BusinessException("Su aplicacion esta actualizada")
        }

        //Obtener las lista de Cambios de todas las versiones que falta por actualizar
        val allChangeLog : MutableList<String> = ArrayList()
        upgradableAppUpdateList.forEach { actualizacion ->
            actualizacion.versionString?.let { allChangeLog.add("Cambios en v$it") }
            actualizacion.description?.let { allChangeLog.addAll(it) }
            //println(actualizacion.versionString)
        }
        lastAppUpdate.description = allChangeLog

        //Si falto alguna actualizacion requerida intermedia dese la actual del cliente hasta la ultima aplicable tambien esa ultima debe ser requerida
        lastAppUpdate.forceUpdate = upgradableAppUpdateList.find { actualizacion -> actualizacion.forceUpdate==true}?.forceUpdate ?:false


        //Obtener el Tamaño real en MB de esa Actualizacion si Existe y enviar Actualizacion correcta al usuario

        return ficheroService.getFileSize(lastAppUpdate.appURL).let {

            if (it.isNullOrEmpty()) {
                throw BusinessException("No disponible por el momento")
            }

            lastAppUpdate.fileSize = it
            lastAppUpdate

        }


//        return try {
//            lastAppUpdate.fileSize = ficheroServicio.getFileSize(lastAppUpdate.appURL)
//            lastAppUpdate
//
//        } catch (e :NotFoundException) {
//            throw BusinessException("No disponible por el momento")
//        }


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


    @Throws(NotFoundException::class)
    override fun deleteAppUpdateById(idActualizacion: String): Boolean {

        val optional:Optional<Actualizacion> = actualizacionRepository.findById(idActualizacion)

        if (optional.isEmpty){
            throw NotFoundException("Actualizacion con ID $idActualizacion No Encontrada").also {
                println(it)
            }
        }

        optional.get().appURL?.let { ficheroService.deleteFile(it) }

        return try {
            actualizacionRepository.deleteById(idActualizacion)
            true
        }catch (e:Exception){
            throw BusinessException(e.message)
        }





    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun getAllAppUpdates():List<Actualizacion>{

        return try {
            val allAppUpdateList =  actualizacionRepository.findAll().sortedBy { it.version }
            allAppUpdateList.forEach {
                it.fileSize = ficheroService.getFileSize(it.appURL)
            }
            allAppUpdateList
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