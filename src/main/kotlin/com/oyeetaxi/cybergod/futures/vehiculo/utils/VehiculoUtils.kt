package com.oyeetaxi.cybergod.futures.vehiculo.utils

import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import java.util.stream.Collectors

object VehiculoUtils {

    fun List<Vehiculo>.filterTipoVehiculos(param: String): List<Vehiculo> {
        return this.stream()
            .parallel()
            .filter {
                (param == it.tipoVehiculo)
            }
            .collect(Collectors.toList())
    }

    fun List<Vehiculo>.filterNoVisibles(param: Boolean): List<Vehiculo> {
        return this.stream()
            .parallel()
            .filter {
                (param != it.visible)
            }
            .collect(Collectors.toList())
    }

    fun List<Vehiculo>.filterActivos(param: Boolean): List<Vehiculo> {
        return this.stream()
            .parallel()
            .filter {
                (param == it.activo)
            }
            .collect(Collectors.toList())
    }

    fun List<Vehiculo>.filterDeshabilitados(param: Boolean): List<Vehiculo> {
        return this.stream()
            .parallel()
            .filter {
                (param != it.habilitado)
            }
            .collect(Collectors.toList())
    }

    fun List<Vehiculo>.filterVerificacionesPendientes(param: Boolean): List<Vehiculo> {
        return this.stream()
            .parallel()
            .filter {
                (param) && (it.vehiculoVerificacion?.verificado == false) && (!it.vehiculoVerificacion?.matricula.isNullOrEmpty()) && (!it.vehiculoVerificacion?.circulacion.isNullOrEmpty()) && (!it.vehiculoVerificacion?.imagenCirculacionURL.isNullOrEmpty())
            }
            .collect(Collectors.toList())
    }


    /**

    ||


     */
}