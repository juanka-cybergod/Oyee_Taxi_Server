package com.oyeetaxi.cybergod.futures.configuracion.models.types

import com.mongodb.lang.Nullable

data class EmailConfiguracion(

    //@Nullable var serviceEmail: String? = null,
    @Nullable var host: String? = null,
    @Nullable var port: Int? = null,
    @Nullable var username: String? = null,
    @Nullable var password: String? = null,

    @Nullable var properties_mail_transport_protocol: String? = null,
    @Nullable var properties_mail_smtp_auth: Boolean? = null,
    @Nullable var properties_mail_smtp_starttls_enable: Boolean? = null,
    @Nullable var properties_mail_debug: Boolean? = null,

    )
/*

        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "oyeetaxioficial@gmail.com"
        mailSender.password = "djmanaxbxxkjzjer"

        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
        return mailSender
 */