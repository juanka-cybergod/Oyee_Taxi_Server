package com.oyeetaxi.cybergod.utils


import com.oyeetaxi.cybergod.futures.fichero.models.TipoFichero
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object Utils {




    fun generateOTP(): String {
        val OTP =  DecimalFormat("000000")
            .format(Random().nextInt(999999))
        return OTP
    }


    fun getServerLocalDate():LocalDate{

        return LocalDate.now()

    }

    fun passwordEncode(password:String): String {
        return Base64.getEncoder().encodeToString(password.toByteArray())
    }

    fun passwordDecode(password:String): String {
        val decodedBytes: ByteArray = Base64.getDecoder().decode(password)
        return String(decodedBytes)
    }


    fun getStringCurrentDateTime():String{
        val dateFormat = SimpleDateFormat("yyyyMMddhhmmss")
        return dateFormat.format(Date()).orEmpty()
        //System.out.println(" C DATE is  "+currentDate)
    }

    fun getFileNameByType(fromId:String, fromTipoFichero:String, ): String{

        val char :Char = '"'
        val id = fromId.replace(char.toString(),"")

        val tipoFichero = TipoFichero.valueOf(
            fromTipoFichero.replace(char.toString(),"")
        )


        val extension: String = "JPG"
        val fileType =
        when (tipoFichero) {
            TipoFichero.USUARIO_PERFIL -> { TipoFichero.USUARIO_PERFIL.name}
            TipoFichero.USUARIO_VERIFICACION -> { TipoFichero.USUARIO_VERIFICACION.name}
            TipoFichero.VEHICULO_FRONTAL -> { TipoFichero.VEHICULO_FRONTAL.name}
            TipoFichero.VEHICULO_CIRCULACION -> { TipoFichero.VEHICULO_CIRCULACION.name}
            else -> {"DESCONOCIADA"}
        }

        return (id + "_" + fileType + "_" + getStringCurrentDateTime() + "." + extension).uppercase()

    }


}