package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable

data class IntervalTimerConfiguracion(

    @Nullable var getAvailableVehicleInterval: Long? = null,
    @Nullable var setDriversLocationInterval: Long? = null,



    )
