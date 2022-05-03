package com.oyeetaxi.cybergod.Modelos

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.Modelos.Config.EmailConfiguracion
import com.oyeetaxi.cybergod.Modelos.Config.TwilioConfiguracion
import com.oyeetaxi.cybergod.Modelos.Config.UpdateConfiguracion
import com.oyeetaxi.cybergod.Utiles.Constants.DEFAULT_CONFIG
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
    @Nullable var smsProvider:SmsProvider? = null,
    @Nullable var emailConfiguracion: EmailConfiguracion? = null,
    @Nullable var updateConfiguracion: UpdateConfiguracion? = null,

    )

