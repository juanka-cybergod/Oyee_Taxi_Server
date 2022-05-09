package com.oyeetaxi.cybergod.Modelos.Config

import com.mongodb.lang.Nullable

data class SocialConfiguracion(

    @Nullable var email: String? = null,
    @Nullable var web: String? = null,
    @Nullable var whatsapp: String? = null,
    @Nullable var facebook: String? = null,
    @Nullable var instagram: String? = null,
    @Nullable var linkedin: String? = null,
    @Nullable var snapchat: String? = null,
    @Nullable var tiktok: String? = null,
    @Nullable var like: String? = null,

    )
