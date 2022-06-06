package com.oyeetaxi.cybergod.futures.share.controller

import com.oyeetaxi.cybergod.configuration.CoroutineConfiguration
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
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
    lateinit var vehiculoService : VehiculoService
    @Autowired
    lateinit var usuarioService : UsuarioService
    @Autowired
    lateinit var tipoVehiculoService : TipoVehiculoService
    @Autowired
    lateinit var emailServicio : EmailServicio
    @Autowired
    lateinit var valoracionService : ValoracionService
    @Autowired
    lateinit var configuracionService : ConfiguracionService
    @Autowired
    lateinit var smsTwilioService : SmsTwilioService
    @Autowired
    lateinit var scope: CoroutineConfiguration


    fun convertVehicleToVehicleResponse(vehiculo: Vehiculo): VehiculoResponse {
            return VehiculoResponse(
                id = vehiculo.id,
                usuario = usuarioService.getUserById(vehiculo.idUsuario!!),
                tipoVehiculo = tipoVehiculoService.getVehicleTypeById(vehiculo.tipoVehiculo!!),
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
                usuarioService.updateUser(
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

            val newValorationAverage = valoracionService.getValorationAverageByUserId(valoracion.idUsuarioValorado?:"")

            usuarioService.updateUser(
                usuario = Usuario(
                    id = valoracion.idUsuarioValorado,
                    valoracion = newValorationAverage
                )
            )
        }
    }


}