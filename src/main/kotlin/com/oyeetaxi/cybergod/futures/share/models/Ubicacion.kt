package com.oyeetaxi.cybergod.futures.share.models

data class Ubicacion(
    var latitud:Double? = null,
    var longitud:Double? = null,
    var rotacion:Int? = 0,
    val direccion:String? = null,
    var alturaMapa:Int? = null
    )
