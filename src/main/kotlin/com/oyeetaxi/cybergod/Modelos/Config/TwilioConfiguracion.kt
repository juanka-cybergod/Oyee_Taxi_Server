package com.oyeetaxi.cybergod.Modelos.Config

import com.mongodb.lang.Nullable

data class TwilioConfiguracion(

    @Nullable val account_sid: String? = null,
    @Nullable val auth_token: String? = null,
    @Nullable val trial_number: String? = null,
    @Nullable val remaningCredit: Double? = 0.0,
    @Nullable val smsCost: Double? = 0.0,


    )
