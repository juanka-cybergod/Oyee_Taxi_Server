package com.oyeetaxi.cybergod.futures.viaje.models

import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse

data class ViajeOferta(
    var conductorVehiculo: VehiculoResponse? = null,
    var precio:Long? = 0L,
    var ofertaAceptada: Boolean? = null,




    )
