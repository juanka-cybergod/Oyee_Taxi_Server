package com.oyeetaxi.cybergod.futures.share.services

import com.oyeetaxi.cybergod.futures.tipo_vehiculo.services.TipoVehiculoService
import com.oyeetaxi.cybergod.futures.usuario.services.UsuarioService
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class BaseService(

) {
    @Autowired lateinit var usuarioService : UsuarioService
    @Autowired lateinit var tipoVehiculoService : TipoVehiculoService


    fun List<Vehiculo>.convertVehicleToVehicleResponse():List<VehiculoResponse> {

        val vehiculoResponseList : MutableList<VehiculoResponse> = ArrayList<VehiculoResponse>()

        this.stream().forEach { vehiculo ->
            vehiculoResponseList.add(
                VehiculoResponse(
                    id = vehiculo.id,
                    usuario = usuarioService.getUserById(vehiculo.idUsuario!!),
                    tipoVehiculo = tipoVehiculoService.getVehicleTypeById(vehiculo.tipoVehiculo!!),
                    marca = vehiculo.marca,
                    modelo = vehiculo.modelo,
                    ano = vehiculo.ano,
                    capacidadPasajeros = vehiculo.capacidadPasajeros,
                    capacidadEquipaje = vehiculo.capacidadEquipaje,
                    capacidadCarga = vehiculo.capacidadCarga,
                    visible = vehiculo.visible,
                    activo = vehiculo.activo,
                    habilitado = vehiculo.habilitado,
                    disponible = vehiculo.disponible,
                    climatizado = vehiculo.climatizado,
                    fechaDeRegistro = vehiculo.fechaDeRegistro,
                    imagenFrontalPublicaURL = vehiculo.imagenFrontalPublicaURL,
                    vehiculoVerificacion = vehiculo.vehiculoVerificacion,
                )
            )
        }


        return vehiculoResponseList.toList()
    }




}