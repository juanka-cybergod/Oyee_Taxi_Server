package com.oyeetaxi.cybergod.Modelos.Config

import com.mongodb.lang.Nullable

data class TwilioConfiguracion(

    @Nullable var account_sid: String? = null,
    @Nullable var auth_token: String? = null,
    @Nullable var trial_number: String? = null,
    @Nullable var remaningCredit: Double? = 0.0,
    @Nullable var smsCost: Double? = 0.0,


    )
