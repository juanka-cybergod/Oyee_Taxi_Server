package com.oyeetaxi.cybergod.futures.viaje.models

import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.models.TipoVehiculo
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "Viajes")
data class Viaje(
    @Id
    @Nullable
    var id:String? = null,
    var estado: ViajeEstado? = ViajeEstado.PENDIENTE,
    var usuario : Usuario? = null,
    var paraTiposVehiculos : List<TipoVehiculo>? = null,
    var origen: Ubicacion? = null,
    var destino: Ubicacion? = null,
    var distancia:String? = null,
    var pasajeros:Int? = null,
    var detallesAdicionales:String? = null,
    var motivoCancelacion:String? = null,
    var ofertasRecibidas:List<ViajeOferta>? = emptyList<ViajeOferta>(),
    var metodoPago: MetodoPago? = MetodoPago.EFECTIVO,
    var soloIda:Boolean? = null,
    var equipaje:Int? = null,
    var fechaHoraSolicitud:LocalDateTime? = null,
    var fechaHoraViaje:LocalDateTime? = null,
    var requiereClimatizado:Boolean?=null

)

