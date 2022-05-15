package com.oyeetaxi.cybergod.futures.provincia.models

import com.mongodb.lang.NonNull
import com.oyeetaxi.cybergod.futures.base.models.Ubicacion
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document



@Document(collection = "provincias")
data class Provincia(
    @NonNull
    @Id
    var nombre:String? = null,
    var ubicacion: Ubicacion? = null,
    var visible :Boolean? = null,
    )
/**

Nombre
ubicacion
alturaMapa
visible

 */