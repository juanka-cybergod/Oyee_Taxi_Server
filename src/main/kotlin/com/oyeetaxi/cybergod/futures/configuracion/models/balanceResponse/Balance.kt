package com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse

import com.fasterxml.jackson.annotation.JsonProperty


data class Balance(
    @JsonProperty("Currency")
    val currency : String? = null,
    @JsonProperty("Balance")
    val balance : Double? = null,
    @JsonProperty("AccountSid")
    val accountSid : String? = null,

    )
