package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance
import com.oyeetaxi.cybergod.futures.share.models.TarjetaCredito

data class PagoConfiguracion(


    @Nullable var tarjetaCredito: TarjetaCredito? = null,
    @Nullable var telefonoConfirmacion :String? = null,
    @Nullable var dias :Long? = null,
    @Nullable var notificarSMS: String? = null,
    @Nullable var notificarCorreo: String? = null,


    )
