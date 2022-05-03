package com.oyeetaxi.cybergod.Servicios

import com.oyeetaxi.cybergod.Interfaces.UsuarioInterface
import com.oyeetaxi.cybergod.Repositorios.UsuarioRepository
import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Modelos.Ubicacion
import com.oyeetaxi.cybergod.Modelos.Usuario
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService : UsuarioInterface {

    @Autowired
    val usuarioRepository: UsuarioRepository? = null

    private var LOGGER = LoggerFactory.getLogger(SmsServicio::class.java)

    @Throws(BusinessException::class)
    override fun getAllUsers(): List<Usuario> {
        try {
            return usuarioRepository!!.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun getUserById(idUsuario: String): Usuario {
        val optional:Optional<Usuario>

        try {
            optional = usuarioRepository!!.findById(idUsuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
        if (!optional.isPresent){
            throw NotFoundException("Usuario con ID $idUsuario No Encontrado")
        }
        return optional.get()
    }




    override fun findUserByPhoneNumber(phoneNumber: String): Usuario? {
        var foundUser: Usuario? = null
        val encontrados: List<Usuario>?

       // LOGGER.info("BUSCANDO A USUARIO CON NUMERO= $phoneNumber")

            encontrados = usuarioRepository!!.findUserByPhoneNumberList(phoneNumber).get()

            if (encontrados.isNotEmpty()) {

                encontrados.forEach {
                        // LOGGER.info("ID Encontrados = "+it.id.toString())
                    }
                    foundUser = encontrados.last()
                   // LOGGER.info("ULTIMO = $foundUser")


            }

        return foundUser
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun addUser(usuario: Usuario): Usuario {
        try {
            return usuarioRepository!!.insert(usuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateUser(usuario: Usuario): Usuario {

        val optional:Optional<Usuario>
        var usuarioActualizado : Usuario = usuario

        usuario.id?.let { id ->

            try {
                optional = usuarioRepository!!.findById(id)
            }catch (e:Exception) {
                throw BusinessException(e.message)
            }

            if (!optional.isPresent){
                throw NotFoundException("Usuario con ID $id No Encontrado")
            } else {

                val usuarioModificar : Usuario = optional.get()

                usuario.conductor?.let { usuarioModificar.conductor = it}
                usuario.modoCondutor?.let { usuarioModificar.modoCondutor = it}
                usuario.nombre?.let { usuarioModificar.nombre = it}
                usuario.apellidos?.let { usuarioModificar.apellidos = it}
                usuario.imagenPerfilURL?.let { usuarioModificar.imagenPerfilURL = it}
                usuario.telefonoMovil?.let { usuarioModificar.telefonoMovil = it}
                usuario.telefonoFijo?.let { usuarioModificar.telefonoFijo = it}
                usuario.contrasena?.let { usuarioModificar.contrasena = it}
                usuario.otpCode?.let { usuarioModificar.otpCode = it }
                usuario.provincia?.let { usuarioModificar.provincia = it}
                usuario.fechaDeNacimiento?.let { usuarioModificar.fechaDeNacimiento = it}
                usuario.fechaDeRegistro?.let { usuarioModificar.fechaDeRegistro = it}
                usuario.habilitado?.let { usuarioModificar.habilitado = it}
                usuario.administrador?.let { usuarioModificar.administrador = it}
                usuario.superAdministrador?.let { usuarioModificar.superAdministrador = it}
                usuario.ubicacion?.let { usuarioModificar.ubicacion = it}
                usuario.mensaje?.let { usuarioModificar.mensaje = it}


                usuario.usuarioVerificacion?.let { verificacion ->
                    verificacion.verificado?.let {  usuarioModificar.usuarioVerificacion?.verificado = it }
                    verificacion.identificacion?.let {  usuarioModificar.usuarioVerificacion?.identificacion = it }
                    verificacion.imagenIdentificaionURL?.let {  usuarioModificar.usuarioVerificacion?.imagenIdentificaionURL = it }
                }


                try {
                    usuarioActualizado = usuarioRepository!!.save(usuarioModificar)

                }catch (e:Exception){

                    throw BusinessException(e.message)

                }

            }

        }

        return usuarioActualizado


//        try {
//            return usuarioRepository!!.save(usuario)
//        }catch (e:Exception) {
//            throw BusinessException(e.message)
//        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countUsers():Long {
        try {
          return  usuarioRepository!!.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllUsers() {

        try {
            usuarioRepository!!.deleteAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteUserById(idUsuario: String) {

        val optional:Optional<Usuario>

        try {
            optional = usuarioRepository!!.findById(idUsuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Usuario con ID $idUsuario No Encontrado")
        } else {

            try {
                usuarioRepository!!.deleteById(idUsuario)
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }




    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun updateUserLocationById(idUsuario: String,ubicacion: Ubicacion): Boolean {

        val optional:Optional<Usuario>
        val usuario :Usuario
        var updated = false

        try {
            optional = usuarioRepository!!.findById(idUsuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Usuario con ID $idUsuario No Encontrado")
        } else {

            usuario = optional.get()
            usuario.ubicacion = ubicacion

            try {
                usuarioRepository!!.save(usuario)
                updated = true
            }catch (e:Exception){
                updated = false
                throw BusinessException(e.message)

            }

        }


        return updated


    }


    override fun getUserByEmail(email: String): Usuario? {
        var foundUser: Usuario? = null
        val encontrados: List<Usuario>?


        encontrados = usuarioRepository!!.findUserByEmail(email).get()

        if (encontrados.isNotEmpty()) {

            encontrados.forEach {
                LOGGER.info("ID Encontrados = "+it.id.toString())
            }
            foundUser = encontrados.last()
            LOGGER.info("ULTIMO = $foundUser")


        }

        return foundUser
    }


    override fun getUserEmailByPhoneNumber(phoneNumber: String): String? {
        return findUserByPhoneNumber(phoneNumber)?.correo
    }


}