package com.oyeetaxi.cybergod.Interfaces




interface EmailInterface {

    fun sendEmailTo(toEmail:String,subject:String,body: String):Boolean
    fun sendEmailToWithAttachment(toEmail: String, subject: String, body: String, attachment:String): Boolean

}




