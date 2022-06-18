package com.oyeetaxi.cybergod.futures.vehiculo.interfaces

import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.models.requestFilter.VehicleFilterOptions
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VehiculoInterface {

    fun getAllVehicles(): List<Vehiculo>
    fun getAviableVehicles(): List<VehiculoResponse>
    fun searchVehiclesPaginatedWithFilter(vehicleFilterOptions: VehicleFilterOptions, pageable: Pageable): Page<VehiculoResponse>
    fun getVehicleById(idVehiculo: String): Vehiculo
    fun addVehicle(vehiculo: Vehiculo): Vehiculo
    fun updateVehicle(vehiculo: Vehiculo): Vehiculo
    fun deleteVehicleById(idVehiculo: String)
    fun deleteAllVehicles()
    fun countVehicles():Long
    fun getActiveVehicleByUserId(idUsuario:String): Vehiculo?
    fun getAllVehiclesFromUserId(idUsuario:String): List<VehiculoResponse>?
    fun setActiveVehicleToUserId(idUsuario:String,idVehiculo:String):Boolean

}


/** TODO FALTA POR HACER
 * Obtener Todos Los Vehiculos de X Usuario
 * Obtener Todos Los Vehiculos Cercanos a X Posicion
 *
 *  ETC ...
 * **/

