package com.oyeetaxi.cybergod.Modelos.Respuestas

import com.oyeetaxi.cybergod.Modelos.Usuario

data class LoginResponse(

    val usuarioEncontrado :Boolean? = null,
    val contrasenaCorrecta: Boolean? = null,
    val mensajeBienvenida : String? = null,

    val usuario: Usuario? = null,
    val vehiculoActivo: VehiculoResponse? = null,



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
