package com.oyeetaxi.cybergod.futures.usuario.models.response


data class RequestVerificationCodeResponse(
    val usuarioId: String? = null,
    val codigoEnviado: Boolean? = null,
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
