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

