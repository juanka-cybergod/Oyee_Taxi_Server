package com.oyeetaxi.cybergod.futures.configuracion.interfaces

import com.oyeetaxi.cybergod.futures.actualizacion.models.Actualizacion
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion


interface ConfiguracionInterface {

    fun isServerActive(): Boolean

    fun isServerActiveForAdmin(): Boolean

    fun getTwilioConfiguration(): TwilioConfiguracion

    fun updateConfiguration(configuracion: Configuracion): Configuracion

    fun getConfiguration(): Configuracion

    fun getSmsProvider(): SmsProvider

    fun getEmailConfiguration(): EmailConfiguracion

}




