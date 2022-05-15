package com.oyeetaxi.cybergod.futures.base.interfaces


interface SmsInterface {
    fun sendSMS(phoneNumber:String, message:String):Boolean


}