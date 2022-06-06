package com.oyeetaxi.cybergod.futures.usuario.models.response

import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse

data class LoginResponse(


    var usuarioEncontrado :Boolean? = null,
    var contrasenaCorrecta: Boolean? = null,
    var servidorActivo: Boolean? = null,
    var mensaje : String? = null,
    var usuario: Usuario? = null,
    var vehiculoActivo: VehiculoResponse? = null,



    )

/**
 *
InicioCorrecto = Boolean
RazonDeInicioIncorrecto = Usuario no Existe / Contraseña Incorrecta / Usuario Deshabilitado
Mensaje de Bienvenida ()
Devolver Provincia
Devolver Tipo de Usuario


 *
 **/
