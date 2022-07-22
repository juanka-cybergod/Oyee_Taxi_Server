package com.oyeetaxi.cybergod.utils

object Constants {

        //private const val URL_API_BASE="/api"
        //private const val URL_API_VERSION="/v1"
        // private const val URL_BASE=URL_API_BASE+URL_API_VERSION
        const val URL_USE_SSL:Boolean = false
        const val DEFAULT_CONFIG = "default"
        const val URL_BASE=""
        const val URL_BASE_USUARIOS="$URL_BASE/usuarios"
        const val URL_BASE_VEHICULOS="$URL_BASE/vehiculos"
        const val URL_BASE_PROVINCIAS="$URL_BASE/provincias"
        const val URL_BASE_TIPO_VEHICULOS="$URL_BASE/tipo_vehiculos"
        const val URL_BASE_CONFIGURACION="$URL_BASE/configuracion"
        const val URL_BASE_ACTUALIZACION="$URL_BASE/actualizacion"
        const val URL_BASE_VIAJES="$URL_BASE/viajes"
        const val FILES_FOLDER="ficheros"
        const val DOWNLOAD_FOLDER="descarga"
        const val URL_BASE_FICHEROS="$URL_BASE/$FILES_FOLDER"
        //const val URL_BASE_SMS="$URL_BASE/sms"
        const val URL_BASE_VALORACION="$URL_BASE/valoraciones"
        //const val UPDATE_FOLDER="actualizacion"

        const val PROPERTIES_mail_transport_protocol="mail.transport.protocol"
        const val PROPERTIES_mail_smtp_auth="mail.smtp.auth"
        const val PROPERTIES_mail_smtp_starttls_enable="mail.smtp.starttls.enable"
        const val PROPERTIES_mail_debug="mail.debug"

        const val TWILIO_BASE_URL = "https://api.twilio.com/2010-04-01/Accounts/"   //https://api.twilio.com/2010-04-01/Accounts/AC9e44b58cdd832019e03a8f045288b591/Balance.json%20-u%20AC9e44b58cdd832019e03a8f045288b591:99e7fb6c2bbcba159a95c503871d4732
        const val AUTHORIZATION = "Authorization"


        const val ERROR_RESPONSE="ERROR_RESPONSE"


        const val DEFAULT_updateAvailableVehiclesRate = 10L

}