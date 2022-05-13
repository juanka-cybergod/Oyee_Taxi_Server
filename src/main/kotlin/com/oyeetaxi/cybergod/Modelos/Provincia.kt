package com.oyeetaxi.cybergod.Modelos

import com.mongodb.lang.NonNull
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