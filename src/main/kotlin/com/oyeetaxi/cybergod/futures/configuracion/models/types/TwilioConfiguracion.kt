package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance

data class TwilioConfiguracion(

    @Nullable var account_sid: String? = null,
    @Nullable var auth_token: String? = null,
    @Nullable var trial_number: String? = null,
//    @Nullable var remainingCredit: Double? = 0.0,
    @Nullable var balance: Balance? = null,
    @Nullable var smsCost: Double? = 0.0,


    )
