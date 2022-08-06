package com.oyeetaxi.cybergod.futures.vehiculo.services


import com.oyeetaxi.cybergod.futures.vehiculo.interfaces.VehiculoInterface
import com.oyeetaxi.cybergod.futures.vehiculo.repositories.VehiculoRepository
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.configuracion.models.types.IntervalTimerConfiguracion
import com.oyeetaxi.cybergod.futures.share.services.BaseService
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.models.requestFilter.VehicleFilterOptions
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.DataResponse
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
import com.oyeetaxi.cybergod.futures.vehiculo.utils.VehiculoUtils.filterActivos
import com.oyeetaxi.cybergod.futures.vehiculo.utils.VehiculoUtils.filterDeshabilitados
import com.oyeetaxi.cybergod.futures.vehiculo.utils.VehiculoUtils.filterTipoVehiculos
import com.oyeetaxi.cybergod.futures.vehiculo.utils.VehiculoUtils.filterVerificacionesPendientes
import com.oyeetaxi.cybergod.futures.vehiculo.utils.VehiculoUtils.filterNoVisibles
import com.oyeetaxi.cybergod.utils.GlobalVariables.availableVehiclesListGlobal
import com.oyeetaxi.cybergod.utils.GlobalVariables.lastTimeUpdateAvailableVehicles
import com.oyeetaxi.cybergod.utils.GlobalVariables.getAvailableVehicleInterval
import com.oyeetaxi.cybergod.utils.GlobalVariables.setDriversLocationInterval
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.min

@Service
class VehiculoService(
    @Autowired private val vehiculoRepository: VehiculoRepository,
) : BaseService(), VehiculoInterface {

    private var LOGGER = LoggerFactory.getLogger(VehiculoService::class.java)

    @Throws(BusinessException::class)
    override fun getData(): DataResponse {

        return try {
            DataResponse(
                vehicleResponseList = getAviableVehicles(),
                intervalTimerConfiguracion = IntervalTimerConfiguracion(
                    getAvailableVehicleInterval = getAvailableVehicleInterval,
                    setDriversLocationInterval = setDriversLocationInterval,
                ),
            )
        } catch(e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getAviableVehicles(): List<VehiculoResponse> {

        val currentTime = System.currentTimeMillis() / 1000
        val diffTime = currentTime - lastTimeUpdateAvailableVehicles

        return try {

            if (diffTime >= getAvailableVehicleInterval || availableVehiclesListGlobal == null) {
                println("$diffTime Seg - Get AvailableVehicles from Repository")
                lastTimeUpdateAvailableVehicles = currentTime
                availableVehiclesListGlobal = vehiculoRepository.findAviableVehicles().convertVehicleToVehicleResponse()
                availableVehiclesListGlobal.orEmpty()
            } else {
                println("$diffTime Seg - Get AvailableVehicles from Global")
                availableVehiclesListGlobal.orEmpty()
            }

        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getActiveVehicleByUserId(idUsuario:String): Vehiculo? {

        var foundVehicle : Vehiculo? = null
        val encontrados: Optional<List<Vehiculo>>

        try {
            encontrados = vehiculoRepository.findActiveVehicleByUserId(idUsuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!encontrados.isPresent){
            throw NotFoundException("El Usuario con ID $idUsuario No Dispone de Vehiculos Activos")
        } else {

            encontrados.get().let {
                if (it.isNotEmpty()) {
                    foundVehicle = it.last()
                }


            }


        }

        return foundVehicle
    }

    @Throws(BusinessException::class)
    override fun getAllVehiclesFromUserId(idUsuario:String): List<VehiculoResponse> {
        try {
            return vehiculoRepository.findAllVehiclesByUserId(idUsuario).convertVehicleToVehicleResponse()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun setActiveVehicleToUserId(idUsuario:String,idVehiculo:String):Boolean {

        var vehiculoActivo = false

        vehiculoRepository.findAllVehiclesByUserId(idUsuario).let { listaVehiculosDeUsuario ->

            listaVehiculosDeUsuario.forEach { vehiculo ->
                when (idVehiculo) {
                    vehiculo.id -> {
                        vehiculoActivo = setActiveVehicle(vehiculo,true)
                    }
                    else -> {
                        setActiveVehicle(vehiculo,false)
                    }
                }
            }


        }

        return vehiculoActivo

    }

    @Throws(BusinessException::class)
    override fun getAllVehicles(): List<Vehiculo> {
        try {
            return vehiculoRepository.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun searchVehiclesPaginatedWithFilter(vehicleFilterOptions: VehicleFilterOptions, pageable: Pageable): Page<VehiculoResponse> {

        var allVehiclesFound: List<Vehiculo> = vehiculoRepository.searchAll(vehicleFilterOptions.texto, pageable.sort)


        with(vehicleFilterOptions) {
            tipoVehiculo?.let { allVehiclesFound = allVehiclesFound.filterTipoVehiculos(it) }
            noVisibles?.let { allVehiclesFound = allVehiclesFound.filterNoVisibles(it) }
            activos?.let { allVehiclesFound = allVehiclesFound.filterActivos(it) }
            deshabilitados?.let { allVehiclesFound = allVehiclesFound.filterDeshabilitados(it) }
            verificacionesPendientes?.let { allVehiclesFound = allVehiclesFound.filterVerificacionesPendientes(it) }
        }



        val start = pageable.offset.toInt()
        val end = min(start + pageable.pageSize, allVehiclesFound.size)

        val vehicleSubList = allVehiclesFound.subList(start, end)

        val vehicleResponseSubList : List<VehiculoResponse> = vehicleSubList.convertVehicleToVehicleResponse()

        return PageImpl(vehicleResponseSubList, pageable, allVehiclesFound.size.toLong())

    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getVehicleById(idVehiculo: String): Vehiculo {
        val optional:Optional<Vehiculo>

        try {
            optional = vehiculoRepository.findById(idVehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Vehiculo con ID $idVehiculo No Encontrado")
        }
        return optional.get()
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun addVehicle(vehiculo: Vehiculo): Vehiculo {
        try {
            return vehiculoRepository.insert(vehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateVehicle(vehiculo: Vehiculo): Vehiculo {

        val optional:Optional<Vehiculo>
        var vehiculoActualizdo : Vehiculo = vehiculo

        vehiculo.id?.let { id ->

            try {
                optional = vehiculoRepository.findById(id)
            }catch (e:Exception) {
                throw BusinessException(e.message)
            }

            if (!optional.isPresent){
                throw NotFoundException("Usuario con ID $id No Encontrado")
            } else {

                val vehiculoModificar : Vehiculo = optional.get()

                vehiculo.idUsuario?.let { vehiculoModificar.idUsuario = it}
                vehiculo.tipoVehiculo?.let { vehiculoModificar.tipoVehiculo = it}
                vehiculo.marca?.let { vehiculoModificar.marca = it}
                vehiculo.modelo?.let { vehiculoModificar.modelo = it}
                vehiculo.ano?.let { vehiculoModificar.ano = it}
                vehiculo.capacidadPasajeros?.let { vehiculoModificar.capacidadPasajeros = it}
                vehiculo.capacidadEquipaje?.let { vehiculoModificar.capacidadEquipaje = it}
                vehiculo.capacidadCarga?.let { vehiculoModificar.capacidadCarga = it}
                vehiculo.visible?.let { vehiculoModificar.visible = it}
                vehiculo.activo?.let { vehiculoModificar.activo = it}
                vehiculo.habilitado?.let { vehiculoModificar.habilitado = it}
                vehiculo.disponible?.let { vehiculoModificar.disponible = it}
                vehiculo.climatizado?.let { vehiculoModificar.climatizado = it}
                vehiculo.fechaDeRegistro?.let { vehiculoModificar.fechaDeRegistro = it}
                vehiculo.imagenFrontalPublicaURL?.let { vehiculoModificar.imagenFrontalPublicaURL = it}

                vehiculo.vehiculoVerificacion?.let { verificacion ->
                    verificacion.verificado?.let {  vehiculoModificar.vehiculoVerificacion?.verificado = it }
                    verificacion.matricula?.let {  vehiculoModificar.vehiculoVerificacion?.matricula = it }
                    verificacion.circulacion?.let {  vehiculoModificar.vehiculoVerificacion?.circulacion = it }
                    verificacion.imagenCirculacionURL?.let {  vehiculoModificar.vehiculoVerificacion?.imagenCirculacionURL = it }
                }


                try {
                    vehiculoActualizdo = vehiculoRepository.save(vehiculoModificar)
                }catch (e:Exception){
                    throw BusinessException(e.message)

                }

            }

        }

        return vehiculoActualizdo

    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countVehicles(): Long {
        try {
            return  vehiculoRepository.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteVehicleById(idVehiculo: String) {

        val optional:Optional<Vehiculo>

        try {
            optional = vehiculoRepository.findById(idVehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Vehiculo con ID $idVehiculo No Encontrado")
        } else {

            try {
                vehiculoRepository.deleteById(idVehiculo)
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }


    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllVehicles() {
        try {
            vehiculoRepository.deleteAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    fun setActiveVehicle(vehiculo: Vehiculo, active:Boolean): Boolean{

        return try {
            updateVehicle(
                Vehiculo(
                    id = vehiculo.id,
                    activo = active

                )
            )
            true
        } catch (e:Exception) {
            false
        }


    }






}