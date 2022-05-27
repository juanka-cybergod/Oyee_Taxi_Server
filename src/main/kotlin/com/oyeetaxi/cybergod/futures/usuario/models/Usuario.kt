package com.oyeetaxi.cybergod.futures.usuario.models


import com.mongodb.lang.Nullable
import com.oyeetaxi.cybergod.futures.provincia.models.Provincia
import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.type.UsuarioVerificacion
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document(collection = "usuarios")
data class Usuario(
    @Id
    @Nullable
    var id:String? = null,
    var conductor:Boolean? = null,
    var modoCondutor: Boolean? = null,
    var nombre:String? = null,
    var apellidos:String? = null,
    var imagenPerfilURL:String? = null,
    var telefonoMovil:String? = null,
    var telefonoFijo:String? = null,
    var correo:String? = null,
    var contrasena:String? = null,
    var otpCode:String?=null,
    var provincia: Provincia? = null,
    var fechaDeNacimiento:LocalDate? = null,
    var fechaDeRegistro:LocalDate? = null,
    var habilitado:Boolean? = null,
    var administrador:Boolean? = null,
    var superAdministrador:Boolean? = null,
    var ubicacion: Ubicacion? = null,
    var usuarioVerificacion: UsuarioVerificacion? = null,
    var valoracion: Float?=null,
    var mensaje: String?=null,
    )







/** Campos q Faltan
valoracion
viajes :
imagenVerificacion
imagenLicancia
mensajeBienvenida/RazonBaneo

 *
 *
var id:String? = null,
var type:String = "",
var name:String = "",
var lastName:String = "",
var profileIMG:String = "",
var phoneMobile:Int? = null,
var phoneFixed:Int? = null,
var email:String = "",
var password:String = "",
var province:String = "",
var dateOfBird:LocalDate? = null,
var dateOfRegister:LocalDate? = null


id_usuario
Tipo_usuario (Chofer/Cliente)
Nombre
Apellidos
Foto_Perfil
Telefono_Movil
Telefono_Fijo
Correo
Contraseña
Provincia
Fecha_de_Nacimiento
Fecha_de_Registro

 **/