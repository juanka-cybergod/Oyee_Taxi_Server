package com.oyeetaxi.cybergod.futures.configuracion.interfaces

import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.SmsProvider
import com.oyeetaxi.cybergod.futures.configuracion.models.types.TwilioConfiguracion
import com.oyeetaxi.cybergod.futures.configuracion.models.types.UpdateConfiguracion


interface ConfiguracionInterface {

    fun isServerActive(): Boolean
    fun isServerActiveForAdmin(): Boolean


    fun setTwilioConfiguration(twilioConfiguracion: TwilioConfiguracion):Boolean
    fun getTwilioConfiguration(): TwilioConfiguracion

    fun updateConfiguration(configuracion: Configuracion): Configuracion
    fun getConfiguration(): Configuracion

    fun updateCredit():Boolean
    fun getRemaningSMS():Int

    fun getSmsProvider(): SmsProvider

    fun getEmailConfiguration(): EmailConfiguracion

    fun getUpdateConfiguration(): UpdateConfiguracion

}




