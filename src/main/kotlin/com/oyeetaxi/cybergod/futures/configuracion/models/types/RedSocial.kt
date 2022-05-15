package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable

data class RedSocial(

    @Nullable var disponible: Boolean? = null,
    @Nullable var nombre: String? = null,
    @Nullable var ico: String? = null,
    @Nullable var url: String? = null,
    @Nullable var ayuda: String? = null,

    )
