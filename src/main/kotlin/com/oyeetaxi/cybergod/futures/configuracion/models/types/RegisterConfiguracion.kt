package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance

data class RegisterConfiguracion(

    @Nullable var smsProvider: SmsProvider? = null,
    @Nullable var habilitadoRegistroConductores: Boolean? = null,
    @Nullable var habilitadoRegistroPasajeros: Boolean? = null,


    )
