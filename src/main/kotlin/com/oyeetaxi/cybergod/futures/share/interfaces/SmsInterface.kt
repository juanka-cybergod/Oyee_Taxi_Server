package com.oyeetaxi.cybergod.futures.share.interfaces

import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance


interface SmsInterface {
    fun sendSMS(phoneNumber:String, message:String):Boolean
    fun getBalance(): Balance?

    fun getRemainingSMS():Int

}