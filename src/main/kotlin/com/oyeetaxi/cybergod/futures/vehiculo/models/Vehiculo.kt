package com.oyeetaxi.cybergod.futures.vehiculo.models


import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.vehiculo.models.type.VehiculoVerificacion
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document(collection = "vehiculos")
data class Vehiculo(
    @Id
    @Nullable
    var id:String? = null,
    var idUsuario:String? = null,
    var tipoVehiculo: String? = null,
    var marca:String? = null,
    var modelo:String? = null,
    var ano:String? = null,
    var capacidadPasajeros: String? = null,
    var capacidadEquipaje: String? = null,
    var capacidadCarga: String? = null,
    var visible: Boolean? = null,
    var activo: Boolean? = null,
    var habilitado: Boolean? = null,
    var disponible: Boolean? = null,
    var climatizado: Boolean? = null,
    var fechaDeRegistro: LocalDate? = null,
    var imagenFrontalPublicaURL:String? = null,
    var vehiculoVerificacion: VehiculoVerificacion? = null,

    )



/**
var id:String? = null,
var idUsuario:String = "",
var tipoVehiculo: String = "",
var marca:String = "",
var modelo:String = "",
var ano:String = "",
var chapa:String = "",
var capacidadPasajeros: Int = 0,
var equipaje: Int = 0,
var visible: Boolean = true,
var habilitado: Boolean = true,
var ocupado: Boolean = false,
var ubicacion: Ubicacion,

var climatizado: Boolean = false,
var fechaDeRegistro: LocalDate? = null,
var imagenPublica:String = "",
var imagenFrontal:String = "",
var imagenCirculacion:String = "",

 **/