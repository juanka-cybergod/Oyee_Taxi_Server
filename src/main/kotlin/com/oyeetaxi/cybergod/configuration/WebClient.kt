package com.oyeetaxi.cybergod.configuration


import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit


@Configuration
class WebClient {





    @Bean
    fun getWebClient(): WebClient{
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 40000)
            .responseTimeout(Duration.ofSeconds(40))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(40, TimeUnit.SECONDS))
                    .addHandlerLast(WriteTimeoutHandler(40, TimeUnit.SECONDS))
            }

        return WebClient.builder()
            //.clientConnector(ReactorClientHttpConnector(httpClient.wiretap(true)))
            .build()
    }
}