package com.oyeetaxi.cybergod.configuration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AppCoroutineScope: CoroutineScope by CoroutineScope(Dispatchers.Default) {

    @Bean
    fun scope(): AppCoroutineScope = AppCoroutineScope()
}