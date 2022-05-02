package com.oyeetaxi.cybergod.Servicios


import com.oyeetaxi.cybergod.Interfaces.VehiculoInterface
import com.oyeetaxi.cybergod.Repositorios.VehiculoRepository
import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Modelos.Vehiculo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class VehiculoService : VehiculoInterface {

    @Autowired
    val vehiculoRepository: VehiculoRepository? = null

    private var LOGGER = LoggerFactory.getLogger(VehiculoService::class.java)

    @Throws(BusinessException::class)
    override fun getActiveVehicleByUserId(idUsuario:String): Vehiculo? {

        var foundVehicle : Vehiculo? = null
        val encontrados: Optional<List<Vehiculo>>

        try {
            encontrados = vehiculoRepository!!.findActiveVehicleByUserId(idUsuario)
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
    override fun getAviableVehicles(): List<Vehiculo> {

        try {
            return vehiculoRepository!!.findAviableVehicles()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getAllVehiclesFromUserId(idUsuario:String): List<Vehiculo> {
        try {
            return vehiculoRepository!!.findAllVehiclesByUserId(idUsuario)
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }





    @Throws(BusinessException::class)
    override fun getAllVehicles(): List<Vehiculo> {
        try {
            return vehiculoRepository!!.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getVehicleById(idVehiculo: String): Vehiculo {
        val optional:Optional<Vehiculo>

        try {
            optional = vehiculoRepository!!.findById(idVehiculo)
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
            return vehiculoRepository!!.insert(vehiculo)
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
                optional = vehiculoRepository!!.findById(id)
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
                //vehiculo.vehiculoVerificacion?.let { vehiculoModificar.vehiculoVerificacion = it}


                try {
                    vehiculoActualizdo = vehiculoRepository!!.save(vehiculoModificar)
                }catch (e:Exception){
                    throw BusinessException(e.message)

                }

            }

        }

        return vehiculoActualizdo


//
//        try {
//            return vehiculoRepository!!.save(vehiculo)
//        }catch (e:Exception) {
//            throw BusinessException(e.message)
//        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countVehicles(): Long {
        try {
            return  vehiculoRepository!!.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteVehicleById(idVehiculo: String) {

        val optional:Optional<Vehiculo>

        try {
            optional = vehiculoRepository!!.findById(idVehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Vehiculo con ID $idVehiculo No Encontrado")
        } else {

            try {
                vehiculoRepository!!.deleteById(idVehiculo)
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }


    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllVehicles() {
        try {
            vehiculoRepository!!.deleteAll()
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