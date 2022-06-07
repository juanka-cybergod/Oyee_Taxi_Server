package com.oyeetaxi.cybergod.futures.vehiculo.models.requestFilter


data class VehicleFilterOptions(
    var texto:String="",
    var tipoVehiculo:String?=null,
    var noVisibles:Boolean?=null,
    var activos:Boolean?=null,
    var deshabilitados:Boolean?=null,
    var verificacionesPendientes:Boolean?=null,
)
