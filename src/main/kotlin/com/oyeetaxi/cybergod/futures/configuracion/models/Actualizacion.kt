package com.oyeetaxi.cybergod.futures.configuracion.models

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.utils.CollectionsNames.APP_UPDATES
import com.oyeetaxi.cybergod.utils.Constants
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = APP_UPDATES)
data class Actualizacion(
    @Id
    @Nullable var id:String? = null,
    @Nullable var active: Boolean? = null,
    @Nullable var version: Int? = null,
    @Nullable var versionString: String? = null,
    @Nullable var fileSize: String? = null,
    @Nullable var appURL: String? = null,
    @Nullable var playStorePackageName: String? = null,
    @Nullable var forceUpdate: Boolean? = null,
    @Nullable var description: List<String>? = null,

    )

