package com.oyeetaxi.cybergod.futures.share.controller

import com.oyeetaxi.cybergod.futures.vehiculo.models.VehiculoResponse
import com.oyeetaxi.cybergod.futures.share.services.EmailServicio
import com.oyeetaxi.cybergod.futures.share.services.SmsTwilioService
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.services.TipoVehiculoService
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.services.UsuarioService
import com.oyeetaxi.cybergod.futures.valoracion.services.ValoracionService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.services.VehiculoService

@RestController
class BaseRestController {

    var LOGGER = LoggerFactory.getLogger(BaseRestController::class.java)


    @Autowired
    val vehiculoBusiness : VehiculoService? = null
    @Autowired
    val usuarioBusiness : UsuarioService? = null
    @Autowired
    val tipoVehiculosBusiness : TipoVehiculoService? = null
    @Autowired
    val emailBusiness : EmailServicio? = null
    @Autowired
    val valoracionBusiness : ValoracionService? = null
    @Autowired
    val configuracionBusiness : ConfiguracionService? = null
    @Autowired
    val smsBusiness : SmsTwilioService? = null


    fun convertVehicleToVehicleResponse(vehiculo: Vehiculo): VehiculoResponse {

        val usuario = usuarioBusiness!!.getUserById(vehiculo.idUsuario!!).apply {
            this.valoracion = valoracionBusiness!!.getValorationAverageByUserId(this.id!!)
        }


            return VehiculoResponse(
                id = vehiculo.id,
                usuario = usuario,
                tipoVehiculo = tipoVehiculosBusiness?.getVehicleTypeById(vehiculo.tipoVehiculo!!),
                marca = vehiculo.marca,
                modelo = vehiculo.modelo,
                ano = vehiculo.ano,
                capacidadPasajeros = vehiculo.capacidadPasajeros,
                capacidadEquipaje = vehiculo.capacidadEquipaje,
                capacidadCarga = vehiculo.capacidadCarga,
                visible = vehiculo.visible,
                activo = vehiculo.activo,
                habilitado = vehiculo.habilitado,
                disponible  = vehiculo.disponible,
                climatizado = vehiculo.climatizado,
                fechaDeRegistro = vehiculo.fechaDeRegistro,
                imagenFrontalPublicaURL = vehiculo.imagenFrontalPublicaURL,
                vehiculoVerificacion = vehiculo.vehiculoVerificacion,
            )

        }

    fun setUserConductorById(idUsuario: String?){
            if (!idUsuario.isNullOrEmpty()){
                usuarioBusiness!!.updateUser(
                    Usuario(
                        id = idUsuario,
                        conductor = true,
                        modoCondutor = true
                    )
                )

            }

        }




}