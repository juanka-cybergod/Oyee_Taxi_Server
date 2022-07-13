package com.oyeetaxi.cybergod.futures.actualizacion.interfaces

import com.oyeetaxi.cybergod.futures.actualizacion.models.Actualizacion


interface ActualizacionInterface {


    fun getAppUpdate(clientAppVersion: Int): Actualizacion

    fun addAppUpdate(actualizacion: Actualizacion): Actualizacion

    fun editAppUpdate(actualizacion: Actualizacion): Actualizacion

    fun deleteAppUpdateById(idActualizacion: String): Boolean

    fun getAllAppUpdates():List<Actualizacion>

    fun setAppUpdateActiveById(idActualizacion: String, active:Boolean):Boolean
}




