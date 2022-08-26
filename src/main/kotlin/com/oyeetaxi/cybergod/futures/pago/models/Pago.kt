package com.oyeetaxi.cybergod.futures.pago.models

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.pago.models.type.EstadoPago
import com.oyeetaxi.cybergod.futures.share.models.TarjetaCredito
import com.oyeetaxi.cybergod.utils.CollectionsNames.PAYMENTS
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document(collection = PAYMENTS)
data class Pago(
    @Id
    @Nullable var id:String? = null,
    @Nullable var idUsuario:String? = null,
    @Nullable var idVehiculo:String? = null,
    @Nullable var tarjetaCreditoOrigen: TarjetaCredito? = null,
    @Nullable var tarjetaCreditoDestino: TarjetaCredito? = null,
    @Nullable var importe:Float? = null,
    @Nullable var diasPagados:Long? = null,
    @Nullable var fechaCreado: LocalDate? = null,
    @Nullable var fechaCompletado: LocalDate? = null,
    @Nullable var estado: EstadoPago? = null,
    )

//    @Nullable var fechaPagadoHasta: LocalDate? = null,