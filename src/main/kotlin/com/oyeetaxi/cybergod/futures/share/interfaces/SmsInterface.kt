package com.oyeetaxi.cybergod.futures.share.interfaces


interface SmsInterface {
    fun sendSMS(phoneNumber:String, message:String):Boolean


}