package com.oyeetaxi.cybergod.Modelos

import com.oyeetaxi.cybergod.Modelos.Respuestas.VehiculoResponse

data class ViajeOferta(
  var conductorVehiculo: VehiculoResponse? = null,
  var precio:Long? = 0L,
  var ofertaAceptada: Boolean? = null,




)
