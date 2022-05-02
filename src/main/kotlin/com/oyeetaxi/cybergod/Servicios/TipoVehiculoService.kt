package com.oyeetaxi.cybergod.Servicios


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Interfaces.TipoVehiculoInterface
import com.oyeetaxi.cybergod.Modelos.TipoVehiculo
import com.oyeetaxi.cybergod.Repositorios.TipoVehiculoRepository
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
    override fun updateVehicleType(vehiculoTipo: TipoVehiculo): TipoVehiculo {
        try {
            return tipoVehiculoRepository!!.save(vehiculoTipo)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
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