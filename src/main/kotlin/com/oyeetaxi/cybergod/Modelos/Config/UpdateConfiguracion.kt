package com.oyeetaxi.cybergod.Modelos.Config

import com.mongodb.lang.Nullable

data class UpdateConfiguracion(

    @Nullable val available: Boolean? = true,
    @Nullable val version: Int? = null,
    @Nullable val versionString: String? = null,
    @Nullable val fileSize: String? = null,
    @Nullable val appURL: String? = null,
    @Nullable val packageName: String? = null,
    @Nullable val forceUpdate: Boolean? = null,
    @Nullable val description: List<String>? = null,

    )
