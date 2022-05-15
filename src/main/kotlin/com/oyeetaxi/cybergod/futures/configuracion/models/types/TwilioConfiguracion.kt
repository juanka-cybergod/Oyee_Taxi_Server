package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable

data class TwilioConfiguracion(

    @Nullable var account_sid: String? = null,
    @Nullable var auth_token: String? = null,
    @Nullable var trial_number: String? = null,
    @Nullable var remainingCredit: Double? = 0.0,
    @Nullable var smsCost: Double? = 0.0,


    )
