package com.oyeetaxi.cybergod.futures.configuracion.models

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SocialConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.UpdateConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "configuracion")
data class Configuracion(
    @Id
    @Nullable var id:String? = DEFAULT_CONFIG,
    @Nullable var servidorActivoClientes: Boolean? = null,
    @Nullable var servidorActivoAdministradores :Boolean? = null,
    @Nullable var motivoServidorInactivoClientes :String? = null,
    @Nullable var motivoServidorInactivoAdministradores :String? = null,
    @Nullable var twilioConfiguracion: TwilioConfiguracion? = null,
    @Nullable var smsProvider: SmsProvider? = null,
    @Nullable var emailConfiguracion: EmailConfiguracion? = null,
    @Nullable var updateConfiguracion: UpdateConfiguracion? = null,
    @Nullable var socialConfiguracion: SocialConfiguracion? = null,

    )

