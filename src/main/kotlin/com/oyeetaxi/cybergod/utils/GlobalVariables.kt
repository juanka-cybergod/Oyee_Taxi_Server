package com.oyeetaxi.cybergod.utils

import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_GetAvailableVehicleInterval
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_SetDriversLocationInterval

object GlobalVariables {


    var getAvailableVehicleInterval: Long = DEFAULT_GetAvailableVehicleInterval
    var setDriversLocationInterval: Long = DEFAULT_SetDriversLocationInterval
    var availableVehiclesListGlobal: List<VehiculoResponse>? = null
    var lastTimeUpdateAvailableVehicles: Long = System.currentTimeMillis() / 1000


}