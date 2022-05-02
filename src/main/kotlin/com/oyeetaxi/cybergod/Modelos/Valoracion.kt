package com.oyeetaxi.cybergod.Modelos


import com.mongodb.lang.Nullable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "valoraciones")
data class Valoracion(
    @Id
    @Nullable
    var id:String? = null,
    var idUsuarioValora:String? = null,
    var idUsuarioValorado:String? = null,
    var valoracion:Float? = null,
    var opinion:String? = null,

)
