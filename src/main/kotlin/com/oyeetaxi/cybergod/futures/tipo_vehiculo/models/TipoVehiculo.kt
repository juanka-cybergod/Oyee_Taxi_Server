package com.oyeetaxi.cybergod.futures.tipo_vehiculo.models

import com.mongodb.lang.Nullable
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "tipo_vehiculo")
data class TipoVehiculo(
    @Id
    @Nullable
    var tipoVehiculo:String? = null,
    var descripcion :String? = null,
    var cuotaMensual: Int? = null,
    var seleccionable : Boolean? = null,
    var prioridadEnMapa: Int? = null,
    var transportePasajeros: Boolean? = null,
    var transporteCarga: Boolean? = null,
    var requiereVerification : Boolean? = null,
    var imagenVehiculoURL:String? = null,

)



/**

Tipo: Strig (Auto,Ban,Motor,Camión,Guagua,Scutter,Motorina)
Descripcion: String
cuotaMensual: Int
Seleccionable: Bool
PrioridadEnMapa: Int ***La Posicion Superficial sobre otros Marcadores (Numero seleccionable = Cantidad de Tipos Actuales + 1)
transportePasajeros
transporteCarga
requiereChapa
requiereCirculacion
requiereLicencia
 **/