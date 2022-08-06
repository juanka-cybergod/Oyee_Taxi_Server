package com.oyeetaxi.cybergod.futures.vehiculo.models.response

import com.oyeetaxi.cybergod.futures.configuracion.models.types.IntervalTimerConfiguracion


data class DataResponse(


    var vehicleResponseList: List<VehiculoResponse>? = null,
    var intervalTimerConfiguracion: IntervalTimerConfiguracion? = null,

    )


