package com.oyeetaxi.cybergod.futures.share.controller

import com.oyeetaxi.cybergod.configuration.CoroutineConfiguration
import com.oyeetaxi.cybergod.futures.vehiculo.models.VehiculoResponse
import com.oyeetaxi.cybergod.futures.share.services.EmailServicio
import com.oyeetaxi.cybergod.futures.share.services.SmsTwilioService
import com.oyeetaxi.cybergod.futures.configuracion.services.ConfiguracionService
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.services.TipoVehiculoService
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.services.UsuarioService
import com.oyeetaxi.cybergod.futures.valoracion.models.Valoracion
import com.oyeetaxi.cybergod.futures.valoracion.services.ValoracionService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.services.VehiculoService
import kotlinx.coroutines.launch

@RestController
class BaseRestController {

    var LOGGER = LoggerFactory.getLogger(BaseRestController::class.java)

    @Autowired
    lateinit var vehiculoBusiness : VehiculoService
    @Autowired
    lateinit var usuarioBusiness : UsuarioService
    @Autowired
    lateinit var tipoVehiculosBusiness : TipoVehiculoService
    @Autowired
    lateinit var emailBusiness : EmailServicio
    @Autowired
    lateinit var valoracionBusiness : ValoracionService
    @Autowired
    lateinit var configuracionBusiness : ConfiguracionService
    @Autowired
    lateinit var smsBusiness : SmsTwilioService
    @Autowired
    lateinit var scope: CoroutineConfiguration


    fun convertVehicleToVehicleResponse(vehiculo: Vehiculo): VehiculoResponse {
            return VehiculoResponse(
                id = vehiculo.id,
                usuario = usuarioBusiness.getUserById(vehiculo.idUsuario!!),
                tipoVehiculo = tipoVehiculosBusiness.getVehicleTypeById(vehiculo.tipoVehiculo!!),
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
                usuarioBusiness.updateUser(
                    Usuario(
                        id = idUsuario,
                        conductor = true,
                        modoCondutor = true
                    )
                )

            }

        }

    fun updateUserValoration(valoracion: Valoracion) {
        scope.launch {

            val newValorationAverage = valoracionBusiness.getValorationAverageByUserId(valoracion.idUsuarioValorado?:"")

            usuarioBusiness.updateUser(
                usuario = Usuario(
                    id = valoracion.idUsuarioValorado,
                    valoracion = newValorationAverage
                )
            )
        }
    }


}