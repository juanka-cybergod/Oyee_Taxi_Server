package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.TipoVehiculo


interface TipoVehiculoInterface {

    fun getAllVehiclesType(): List<TipoVehiculo>
    fun getVehicleTypeById(idTipoVehiculo: String): TipoVehiculo
    fun addVehicleType(tipoVehiculo: TipoVehiculo): TipoVehiculo
    fun updateVehicleType(tipoVehiculo: TipoVehiculo): TipoVehiculo
    fun deleteVehicleTypeById(idTipoVehiculo: String)
    fun deleteAllVehiclesType()
    fun countVehiclesType():Long

}


/** TODO FALTA POR HACER
 * Obtener Todos Los Vehiculos de X Usuario
 * Obtener Todos Los Vehiculos Cercanos a X Posicion
 *
 *  ETC ...
 * **/

