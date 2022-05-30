package com.oyeetaxi.cybergod.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClient {



    @Bean
    fun getWebClientBuilder(): WebClient.Builder{
        return WebClient.builder()
    }
}