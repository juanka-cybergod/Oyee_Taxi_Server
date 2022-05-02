package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.Vehiculo
import java.util.*

interface VehiculoInterface {

    fun getAllVehicles(): List<Vehiculo>
    fun getAviableVehicles(): List<Vehiculo>
    fun getVehicleById(idVehiculo: String): Vehiculo
    fun addVehicle(vehiculo: Vehiculo): Vehiculo
    fun updateVehicle(vehiculo: Vehiculo): Vehiculo
    fun deleteVehicleById(idVehiculo: String)
    fun deleteAllVehicles()
    fun countVehicles():Long

    fun getActiveVehicleByUserId(idUsuario:String): Vehiculo?

    fun getAllVehiclesFromUserId(idUsuario:String): List<Vehiculo>?

}


/** TODO FALTA POR HACER
 * Obtener Todos Los Vehiculos de X Usuario
 * Obtener Todos Los Vehiculos Cercanos a X Posicion
 *
 *  ETC ...
 * **/

