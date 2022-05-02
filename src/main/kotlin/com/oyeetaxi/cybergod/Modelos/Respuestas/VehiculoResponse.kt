package com.oyeetaxi.cybergod.Modelos.Respuestas


import com.oyeetaxi.cybergod.Modelos.TipoVehiculo
import com.oyeetaxi.cybergod.Modelos.Usuario
import com.oyeetaxi.cybergod.Modelos.Verificacion.VehiculoVerificacion
import java.time.LocalDate



data class VehiculoResponse(
    var id:String? = null,
    var usuario: Usuario? = null,
    var tipoVehiculo: TipoVehiculo? = null,
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


