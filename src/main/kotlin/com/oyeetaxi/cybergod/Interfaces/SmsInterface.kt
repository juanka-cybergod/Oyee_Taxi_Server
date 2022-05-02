package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.SmsRequest


interface SmsInterface {

    fun sendSMS(smsRequest: SmsRequest):Boolean
 //   fun generateOTP():String



}