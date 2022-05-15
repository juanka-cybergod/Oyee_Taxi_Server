package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable

data class UpdateConfiguracion(

    @Nullable var available: Boolean? = true,
    @Nullable var version: Int? = null,
    @Nullable var versionString: String? = null,
    @Nullable var fileSize: String? = null,
    @Nullable var appURL: String? = null,
    @Nullable var packageName: String? = null,
    @Nullable var forceUpdate: Boolean? = null,
    @Nullable var description: List<String>? = null,

    )
