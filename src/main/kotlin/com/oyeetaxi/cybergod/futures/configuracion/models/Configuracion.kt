package com.oyeetaxi.cybergod.futures.configuracion.models

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.configuracion.models.types.*
import com.oyeetaxi.cybergod.utils.CollectionsNames.CONFIGURACION
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = CONFIGURACION)
data class Configuracion(
    @Id
    @Nullable var id:String? = DEFAULT_CONFIG,
    @Nullable var servidorActivoClientes: Boolean? = null,
    @Nullable var servidorActivoAdministradores :Boolean? = null,
    @Nullable var motivoServidorInactivoClientes :String? = null,
    @Nullable var motivoServidorInactivoAdministradores :String? = null,
    @Nullable var twilioConfiguracion: TwilioConfiguracion? = null,
    @Nullable var emailConfiguracion: EmailConfiguracion? = null,
    @Nullable var actualizacionHabilita: Boolean? = null,
    @Nullable var socialConfiguracion: SocialConfiguracion? = null,
    @Nullable var intervalTimerConfiguracion: IntervalTimerConfiguracion? = null,
    @Nullable var registerConfiguracion: RegisterConfiguracion? = null,

    @Nullable var pagoConfiguracion: PagoConfiguracion? = null,//



    )

