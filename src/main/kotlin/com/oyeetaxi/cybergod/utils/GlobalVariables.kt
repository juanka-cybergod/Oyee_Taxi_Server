package com.oyeetaxi.cybergod.utils

import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse

object GlobalVariables {



    var updateAvailableVehiclesRate: Long = 10
    var availableVehiclesListGlobal: List<VehiculoResponse>? = null
    var lastTimeUpdateAvailableVehicles: Long = System.currentTimeMillis() / 1000


}