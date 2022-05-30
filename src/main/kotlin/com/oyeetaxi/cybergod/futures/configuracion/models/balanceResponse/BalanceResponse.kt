package com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse

import com.fasterxml.jackson.annotation.JsonProperty

data class BalanceResponse(
    @JsonProperty("Balance")
    var balance: Balance? = null
)
