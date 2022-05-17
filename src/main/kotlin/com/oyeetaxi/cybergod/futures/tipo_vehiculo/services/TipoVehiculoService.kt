package com.oyeetaxi.cybergod.futures.tipo_vehiculo.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.interfaces.TipoVehiculoInterface
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.models.TipoVehiculo
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.repositories.TipoVehiculoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TipoVehiculoService : TipoVehiculoInterface {

    @Autowired
    val tipoVehiculoRepository: TipoVehiculoRepository? = null


    @Throws(BusinessException::class)
    override fun getAllVehiclesType(): List<TipoVehiculo> {
        try {
            return tipoVehiculoRepository!!.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getAvailableVehiclesType(): List<TipoVehiculo> {
        try {
            return tipoVehiculoRepository!!.findAvailableVehiclesType()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }



    @Throws(BusinessException::class,NotFoundException::class)
    override fun getVehicleTypeById(idTipoVehiculo: String): TipoVehiculo {
        val optional:Optional<TipoVehiculo>

        try {
            optional = tipoVehiculoRepository!!.findById(idTipoVehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Tipo de Vehiculo con ID $idTipoVehiculo No Encontrado")
        }
        return optional.get()
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun addVehicleType(vehiculoTipo: TipoVehiculo): TipoVehiculo {
        try {
            return tipoVehiculoRepository!!.insert(vehiculoTipo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateVehicleType(tipoVehiculo: TipoVehiculo): TipoVehiculo {

        val optional:Optional<TipoVehiculo>
        var tipoVehiculoActualizado: TipoVehiculo = tipoVehiculo

        tipoVehiculo.tipoVehiculo?.let { tipoVehiculoNombre ->

            try{
                optional= tipoVehiculoRepository!!.findById(tipoVehiculoNombre)
            }   catch (e: Exception) {
                throw  BusinessException(e.message)
            }


            if (!optional.isPresent) {
                throw  NotFoundException("Tipo de Vehículo $tipoVehiculoNombre no Encontrado")
            } else {
                val tipoVehiculoModificar : TipoVehiculo = optional.get()

                tipoVehiculo.descripcion?.let { tipoVehiculoModificar.descripcion = it }
                tipoVehiculo.cuotaMensual?.let { tipoVehiculoModificar.cuotaMensual = it }
                tipoVehiculo.seleccionable?.let { tipoVehiculoModificar.seleccionable = it }
                tipoVehiculo.prioridadEnMapa?.let { tipoVehiculoModificar.prioridadEnMapa = it }
                tipoVehiculo.transportePasajeros?.let { tipoVehiculoModificar.transportePasajeros = it }
                tipoVehiculo.transporteCarga?.let { tipoVehiculoModificar.transporteCarga = it }
                tipoVehiculo.requiereVerification?.let { tipoVehiculoModificar.requiereVerification = it }
                tipoVehiculo.imagenVehiculoURL?.let { tipoVehiculoModificar.imagenVehiculoURL = it }



                try {
                    tipoVehiculoActualizado = tipoVehiculoRepository!!.save(tipoVehiculoModificar)
                } catch (e : Exception) {
                    throw BusinessException(e.message)
                }

            }




        }


        return tipoVehiculoActualizado

//        try {
//            return tipoVehiculoRepository!!.save(vehiculoTipo)
//        }catch (e:Exception) {
//            throw BusinessException(e.message)
//        }

    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countVehiclesType(): Long {
        try {
            return  tipoVehiculoRepository!!.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteVehicleTypeById(idTipoVehiculo: String) {

        val optional:Optional<TipoVehiculo>

        try {
            optional = tipoVehiculoRepository!!.findById(idTipoVehiculo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Tipo de Vehiculo con ID $idTipoVehiculo No Encontrado")
        } else {

            try {
                tipoVehiculoRepository!!.deleteById(idTipoVehiculo)
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllVehiclesType() {
        try {
            tipoVehiculoRepository!!.deleteAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }








}