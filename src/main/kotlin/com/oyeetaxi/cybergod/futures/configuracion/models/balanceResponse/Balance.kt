package com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse

import com.fasterxml.jackson.annotation.JsonProperty


data class Balance(
    @JsonProperty("Currency")
    var currency : String? = null,
    @JsonProperty("Balance")
    var balance : Double? = null,
    @JsonProperty("AccountSid")
    var accountSid : String? = null,

    )
