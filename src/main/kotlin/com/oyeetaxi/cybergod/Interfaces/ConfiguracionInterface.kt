package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.Configuracion
import com.oyeetaxi.cybergod.Modelos.Config.EmailConfiguracion
import com.oyeetaxi.cybergod.Modelos.SmsProvider
import com.oyeetaxi.cybergod.Modelos.Config.TwilioConfiguracion
import com.oyeetaxi.cybergod.Modelos.Config.UpdateConfiguracion


interface ConfiguracionInterface {

    fun isServerActive(): Boolean
    fun isServerActiveForAdmin(): Boolean


    fun setTwilioConfiguration(twilioConfiguracion: TwilioConfiguracion):Boolean
    fun getTwilioConfiguration(): TwilioConfiguracion

    fun updateConfiguration(configuracion: Configuracion):Configuracion
    fun getConfiguration():Configuracion

    fun updateCredit():Boolean
    fun getRemaningSMS():Int

    fun getSmsProvider():SmsProvider

    fun getEmailConfiguration(): EmailConfiguracion

    fun getUpdateConfiguration(): UpdateConfiguracion

}




