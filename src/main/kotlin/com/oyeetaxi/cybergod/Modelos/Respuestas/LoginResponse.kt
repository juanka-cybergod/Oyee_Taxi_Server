package com.oyeetaxi.cybergod.Modelos.Respuestas

import com.oyeetaxi.cybergod.Modelos.Usuario

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
