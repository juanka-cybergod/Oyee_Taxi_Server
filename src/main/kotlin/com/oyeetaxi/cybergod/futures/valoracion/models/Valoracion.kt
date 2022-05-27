package com.oyeetaxi.cybergod.futures.valoracion.models


import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.utils.CollectionsNames.VALORACIONES
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = VALORACIONES)
data class Valoracion(
    @Id
    @Nullable
    var id:String? = null,
    var idUsuarioValora:String? = null,
    var idUsuarioValorado:String? = null,
    var valoracion:Float? = null,
    var opinion:String? = null,

)
