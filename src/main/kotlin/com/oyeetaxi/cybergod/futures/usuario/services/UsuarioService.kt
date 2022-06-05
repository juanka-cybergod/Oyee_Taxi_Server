package com.oyeetaxi.cybergod.futures.usuario.services

import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.interfaces.UsuarioInterface
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.models.requestFilter.UserFilterOptions
import com.oyeetaxi.cybergod.futures.usuario.repositories.UsuarioRepository
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterAdministradores
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterConductores
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterDeshabilitados
import com.oyeetaxi.cybergod.futures.usuario.utils.UsuarioUtils.filterVerificacionesPendientes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.min


@Service
class UsuarioService(
    @Autowired private val usuarioRepository: UsuarioRepository
) : UsuarioInterface {



    @Autowired
    val usuarioQueryService: UsuarioQueryService? = null

    @Throws(BusinessException::class)
    override fun getAllUsers(): List<Usuario> {
        try {
            return usuarioRepository.findAll()
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun getAllUsersPaginated(pageable: Pageable): Page<Usuario> {
        try {
            return usuarioRepository.findAll(pageable)
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class)
    override fun searchAllUsersPaginated( search: String, pageable: Pageable): Page<Usuario> {
        try {
            return usuarioRepository.search(search,pageable)
        } catch (e:Exception){
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun searchUsersPaginatedWithFilter(userFilterOptions: UserFilterOptions, pageable: Pageable): Page<Usuario> {


//        var allUserFound: List<Usuario> =  try{
//           usuarioQueryService!!.searchUsersFiltered(search, userFilterOptions, pageable.sort)
//        } catch (e:Exception){
//            println("usuarioQueryService!!.searchUsersFiltered() -> Fail to Search")
//           usuarioRepository!!.searchAll(search, pageable.sort)
//        }

        var allUserFound: List<Usuario> = usuarioRepository.searchAll(userFilterOptions.texto, pageable.sort)


        with(userFilterOptions) {
                condutores?.let { allUserFound = allUserFound.filterConductores(it) }
                deshabilitados?.let { allUserFound = allUserFound.filterDeshabilitados(it) }
                administradores?.let { allUserFound = allUserFound.filterAdministradores(it) }
                verificacionesPendientes?.let { allUserFound = allUserFound.filterVerificacionesPendientes(it) }
        }



        val start = pageable.offset.toInt()
        val end = min(start + pageable.pageSize, allUserFound.size)

        val userSubList = allUserFound.subList(start, end)


        return PageImpl(userSubList, pageable, allUserFound.size.toLong())

    }



    @Throws(BusinessException::class,NotFoundException::class)
    override fun getUserById(idUsuario: String): Usuario {
        val optional:Optional<Usuario>

        try {
            optional = usuarioRepository.findById(idUsuario)
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

            encontrados = usuarioRepository.findUserByPhoneNumberList(phoneNumber).get()

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
            return usuarioRepository.insert(usuario)
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
                optional = usuarioRepository.findById(id)
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
                usuario.valoracion?.let { usuarioModificar.valoracion = it}

                usuario.usuarioVerificacion?.let { verificacion ->
                    verificacion.verificado?.let {  usuarioModificar.usuarioVerificacion?.verificado = it }
                    verificacion.identificacion?.let {  usuarioModificar.usuarioVerificacion?.identificacion = it }
                    verificacion.imagenIdentificaionURL?.let {  usuarioModificar.usuarioVerificacion?.imagenIdentificaionURL = it }
                }


                try {
                    usuarioActualizado = usuarioRepository.save(usuarioModificar)

                }catch (e:Exception){

                    throw BusinessException(e.message)

                }

            }

        }

        return usuarioActualizado


    }

    @Throws(BusinessException::class,NotFoundException::class)
    override fun countUsers():Long {
        try {
          return  usuarioRepository.count()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteAllUsers() {

        try {
            usuarioRepository.deleteAll()
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }


    }


    @Throws(BusinessException::class,NotFoundException::class)
    override fun deleteUserById(idUsuario: String) {

        val optional:Optional<Usuario>

        try {
            optional = usuarioRepository.findById(idUsuario)
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
        val usuario : Usuario
        var updated = false

        try {
            optional = usuarioRepository.findById(idUsuario)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }

        if (!optional.isPresent){
            throw NotFoundException("Usuario con ID $idUsuario No Encontrado")
        } else {

            usuario = optional.get()
            usuario.ubicacion = ubicacion

            return try {
                usuarioRepository.save(usuario)
                true
            }catch (e:Exception){
                throw BusinessException(e.message)
            }

        }





    }


    override fun getUserByEmail(email: String): Usuario? {
        var foundUser: Usuario? = null
        val encontrados: List<Usuario>?


        encontrados = usuarioRepository.findUserByEmail(email).get()

        if (encontrados.isNotEmpty()) {

            //encontrados.forEach {
                //LOGGER.info("ID Encontrados = "+it.id.toString())
            //}
            foundUser = encontrados.last()
            //LOGGER.info("ULTIMO = $foundUser")


        }

        return foundUser
    }


    override fun getUserEmailByPhoneNumber(phoneNumber: String): String? {
        return findUserByPhoneNumber(phoneNumber)?.correo
    }


}