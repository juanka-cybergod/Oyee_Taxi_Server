package com.oyeetaxi.cybergod.Configuracion

import com.oyeetaxi.cybergod.Utiles.Constants.URL_USE_SSL
import org.apache.catalina.Context
import org.apache.catalina.connector.Connector
import org.apache.tomcat.util.descriptor.web.SecurityCollection
import org.apache.tomcat.util.descriptor.web.SecurityConstraint
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration





@Configuration
class SSLConfiguration {

    @Bean
    fun servletContainer(): ServletWebServerFactory {

        val tomcat: TomcatServletWebServerFactory = object : TomcatServletWebServerFactory() {
            override fun postProcessContext(context: Context) {
                val securityConstraint = SecurityConstraint()
                securityConstraint.userConstraint = "CONFIDENTIAL"
                val collection = SecurityCollection()
                collection.addPattern("/*")
                securityConstraint.addCollection(collection)
                if (URL_USE_SSL) {
                    context.addConstraint(securityConstraint)
                }


            }
        }
        if (URL_USE_SSL) {
            tomcat.addAdditionalTomcatConnectors(redirectConnector())
        }

        return tomcat

    }


    //Redireccionar las entradas de http://IP hacia https://IP:8443
    fun redirectConnector(): Connector {
        val connector = Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL)
        connector.scheme = "http"
        connector.port = 80
        connector.secure = false
        connector.redirectPort = 8443
        return connector
    }


}