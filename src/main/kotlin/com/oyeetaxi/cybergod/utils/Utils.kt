package com.oyeetaxi.cybergod.utils


import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.BalanceResponse
import com.oyeetaxi.cybergod.futures.fichero.models.TipoFichero
import io.jsonwebtoken.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object Utils {






    fun getEncodedAuthorization(userId:String,token:String): String {
        val typeEncode = "Basic "
        val authorization = "${userId}:${token}"
        return typeEncode + Base64.getEncoder().encodeToString(authorization.toByteArray())
    }


    fun String.getBalanceFromXMLResponse():Balance?{
        val value : BalanceResponse? = try {
            XmlMapper().readValue(this, BalanceResponse::class.java)
        } catch ( e : IOException) {
            println("Fail To Convert XML to DataClass : BAD XML = $this\n CAUSE IOException: ${e.message}")
            null
        }
        return value?.balance
    }


    fun generateOTP(): String {
        return DecimalFormat("000000").format(Random().nextInt(999999))
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