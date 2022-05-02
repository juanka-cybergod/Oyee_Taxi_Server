package com.oyeetaxi.cybergod.Modelos.Config

import com.mongodb.lang.Nullable

data class EmailConfiguracion(

    @Nullable val serviceEmail: String? = null,
    @Nullable val host: String? = null,
    @Nullable val port: Int? = null,
    @Nullable val username: String? = null,
    @Nullable val password: String? = null,

    @Nullable val properties_mail_transport_protocol: String? = null,
    @Nullable val properties_mail_smtp_auth: Boolean? = null,
    @Nullable val properties_mail_smtp_starttls_enable: Boolean? = null,
    @Nullable val properties_mail_debug: Boolean? = null,

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