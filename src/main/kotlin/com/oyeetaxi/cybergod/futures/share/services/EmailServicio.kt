package com.oyeetaxi.cybergod.futures.share.services

import com.oyeetaxi.cybergod.futures.share.interfaces.EmailInterface
import com.oyeetaxi.cybergod.futures.configuracion.models.types.EmailConfiguracion
import com.oyeetaxi.cybergod.futures.usuario.repositories.UsuarioRepository
import com.oyeetaxi.cybergod.utils.Constants.PROPERTIES_mail_debug
import com.oyeetaxi.cybergod.utils.Constants.PROPERTIES_mail_smtp_auth
import com.oyeetaxi.cybergod.utils.Constants.PROPERTIES_mail_smtp_starttls_enable
import com.oyeetaxi.cybergod.utils.Constants.PROPERTIES_mail_transport_protocol
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import java.util.*


@Service
class EmailServicio : EmailInterface {

    @Autowired
    val usuarioRepository: UsuarioRepository? = null

    @Autowired
    val configuracionBusiness : ConfiguracionService? = null

    private var LOGGER = LoggerFactory.getLogger(EmailServicio::class.java)

    lateinit var emailConfiguration: EmailConfiguracion

    private fun getJavaMailSender(): JavaMailSender? {

        emailConfiguration  = configuracionBusiness!!.getEmailConfiguration()
        val mailSender = JavaMailSenderImpl()
        val props: Properties = mailSender.javaMailProperties

        mailSender.host = emailConfiguration.host
        mailSender.port = emailConfiguration.port!!
        mailSender.username = emailConfiguration.username
        mailSender.password = emailConfiguration.password
        props[PROPERTIES_mail_transport_protocol] = emailConfiguration.properties_mail_transport_protocol
        props[PROPERTIES_mail_smtp_auth] = emailConfiguration.properties_mail_smtp_auth
        props[PROPERTIES_mail_smtp_starttls_enable] = emailConfiguration.properties_mail_smtp_starttls_enable
        props[PROPERTIES_mail_debug] = emailConfiguration.properties_mail_debug
        return mailSender
    }

    override fun sendEmailTo(toEmail: String, subject: String, body: String): Boolean {

        val javaMailSender: JavaMailSender? = getJavaMailSender()

        val message = SimpleMailMessage().apply {
            setFrom(emailConfiguration.username!!) //setFrom(emailConfiguration.serviceEmail!!)
            setSubject(subject)
            setTo(toEmail)
            setText(body)
        }

        return try {
            javaMailSender?.send(message)
            LOGGER.info("Email Enviado")
            true
        } catch (e: Exception) {
            LOGGER.info("Error al Enviar Email")
            false
        }




    }


    override fun sendEmailToWithAttachment(toEmail: String, subject: String, body: String, attachment:String): Boolean {



        val file = FileSystemResource(File(attachment))


        val javaMailSender : JavaMailSender? = getJavaMailSender()


        val mimeMessage = javaMailSender?.createMimeMessage()

        val mimeMessageHelper = MimeMessageHelper(mimeMessage!!,true).apply {
            setFrom(emailConfiguration.username!!) //setFrom(emailConfiguration.serviceEmail!!)
            setSubject(subject)
            setTo(toEmail)
            setText(body)
            addAttachment(file.filename,file)
        }





        return try {
            javaMailSender.send(mimeMessage)
            LOGGER.info("Email con Adjuntos Enviado")
            true
        } catch (e: Exception) {
            LOGGER.info("Error al Enviar Email con Adjuntos")
            false
        }




    }




}

//    private fun getJavaMailSender(): JavaMailSender? {
//        val emailConfiguration :EmailConfiguracion = configuracionBusiness!!.getEmailConfiguration()
//
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = "smtp.gmail.com"
//        mailSender.port = 587
//        mailSender.username = "oyeetaxioficial@gmail.com"
//        mailSender.password = "djmanaxbxxkjzjer"
//        val props: Properties = mailSender.javaMailProperties
//        props[PROPERTIES_mail_transport_protocol] = "smtp"
//        props[PROPERTIES_mail_smtp_auth] = "true"
//        props[PROPERTIES_mail_smtp_starttls_enable] = "true"
//        props[PROPERTIES_mail_debug] = "true"
//        return mailSender
//    }

