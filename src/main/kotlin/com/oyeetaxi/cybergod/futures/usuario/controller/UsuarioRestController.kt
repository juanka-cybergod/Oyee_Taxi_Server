package com.oyeetaxi.cybergod.futures.usuario.controller


import com.oyeetaxi.cybergod.futures.share.controller.BaseRestController
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.usuario.models.response.LoginResponse
import com.oyeetaxi.cybergod.futures.usuario.models.response.RequestVerificationCodeResponse
import com.oyeetaxi.cybergod.futures.vehiculo.models.VehiculoResponse
import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.models.requestFilter.UserFilterOptions
import com.oyeetaxi.cybergod.utils.Constants
import com.oyeetaxi.cybergod.utils.Utils
import com.oyeetaxi.cybergod.utils.Utils.generateOTP
import com.oyeetaxi.cybergod.utils.Utils.getServerLocalDate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(Constants.URL_BASE_USUARIOS)
class UsuarioRestController: BaseRestController() {


    @GetMapping("/requestVerificationCodeToRestorePassword")
    fun requestVerificationCodeToRestorePassword(@RequestParam("emailOrPhone") emailOrPhone: String): ResponseEntity<RequestVerificationCodeResponse> {

        val requestVerificationCodeResponse : RequestVerificationCodeResponse?
        var usuario: Usuario? = null
        var emailCorrecto :String? = null
        var emailSentSussefuctly : Boolean = false


        if (emailOrPhone.contains("@",true)) {
            //Buscar por email
            usuario = usuarioBusiness!!.getUserByEmail(emailOrPhone)
            usuario?.let {
                emailCorrecto = it.correo
            }

        } else {
            //buscar por numero de telefono

            usuario =  usuarioBusiness!!.findUserByPhoneNumber(emailOrPhone)
            usuario?.let {
                emailCorrecto = it.correo
            }

        }



        //Email Encontrado y Correcto
        emailCorrecto?.let {

            val codeOTP = Utils.generateOTP()

            val template = """
            Hola ${usuario?.nombre} usted está intentando restablecer su contraseña en Oyee Taxi, use el siguiente código para restablecerla [ $codeOTP ]
            
            Gracias por usar nuestros servicios
        """.trimIndent()

            val asunto = "Oyee Taxi"

            emailSentSussefuctly = emailBusiness!!.sendEmailTo(it,asunto, template)
            if (emailSentSussefuctly) {
                usuarioBusiness!!.updateUser(
                    Usuario(
                        id = usuario?.id,
                        otpCode = codeOTP
                    )
                )
            }


        }

        requestVerificationCodeResponse = RequestVerificationCodeResponse(
            usuarioId = usuario?.id,
            codigoEnviado = emailSentSussefuctly,
        )


        return try {
            ResponseEntity(requestVerificationCodeResponse,HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }

    @GetMapping("/verifyOTPCodeToRestorePassword")
    fun verifyOTPCodeToRestorePassword(@RequestParam("idUsuario") idUsuario: String,@RequestParam("otpCode") otpCode: String): ResponseEntity<Any>{

        var otpCodeCorrect  = false

        val usuario : Usuario? = try {
            usuarioBusiness!!.getUserById(idUsuario)
        }catch (e:BusinessException) {
            null
        }

        usuario?.let { userFound ->
            otpCodeCorrect = userFound.otpCode == otpCode
        }


        return try {
            ResponseEntity(otpCodeCorrect,HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }


    @GetMapping("/loginUser")
    fun loginUser(@RequestParam("userPhone") userPhone: String, @RequestParam("password") password: String): ResponseEntity<LoginResponse> {

        var userFound : Usuario?=null
        var usuarioEncontrado  = false
        var serverActiveForThisUser = true
        var activeVehicleResponse : VehiculoResponse? = null
        var correctPasword  = false
        var messaje:String? = null



            usuarioBusiness!!.findUserByPhoneNumber(userPhone)?.let { usuario ->
                usuarioEncontrado = true
                correctPasword = (password == usuario.contrasena)
                if (correctPasword) {


                    if (usuario.conductor == true) {
                        vehiculoBusiness!!.getActiveVehicleByUserId(usuario.id!!)?.let { vehiculo ->
                            activeVehicleResponse = convertVehicleToVehicleResponse(vehiculo)
                        }

                    }

                    val valoracion = valoracionBusiness!!.getValorationAverageByUserId(usuario.id!!)
                    usuario.valoracion =  valoracion


                    val serverConfig = configuracionBusiness!!.getConfiguration()
                    if (usuario.administrador == true) {
                        serverActiveForThisUser = serverConfig.servidorActivoAdministradores?:true
                        if (!serverActiveForThisUser) {messaje = serverConfig.motivoServidorInactivoAdministradores}
                    } else if (usuario.administrador == false && usuario.superAdministrador == false) {
                        serverActiveForThisUser = serverConfig.servidorActivoClientes?:true
                        if (!serverActiveForThisUser) {messaje = serverConfig.motivoServidorInactivoClientes}
                    }
                    if (usuario.superAdministrador == true) {
                        serverActiveForThisUser = true
                        messaje = null
                    }


                    userFound = usuario


                }

            }





        val loginResponse = LoginResponse(
            usuarioEncontrado = usuarioEncontrado,
            contrasenaCorrecta = correctPasword,
            servidorActivo = serverActiveForThisUser,
            mensaje = messaje,
            usuario = userFound,
            vehiculoActivo = activeVehicleResponse
        )


        return try {
            ResponseEntity(loginResponse,HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @GetMapping("/userExistByPhone")
    fun userExistByPhone(@RequestParam("userPhone") userPhone: String): ResponseEntity<Boolean> {

        var usuarioEncontrado  = false

        usuarioBusiness!!.findUserByPhoneNumber(userPhone)?.let {
            usuarioEncontrado = true
        }



        return try {
            ResponseEntity(usuarioEncontrado,HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }


    @GetMapping("/requestOTPCodeToSMSTest")
    fun requestOTPCodeToSMSTest(@RequestParam("phoneNumber") phoneNumber: String): ResponseEntity<Any> {
        return try {
            val codeOTP = 123456 //smsBusiness!!.generateOTP()
            ResponseEntity(codeOTP, HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/requestOTPCodeToSMS")
    fun requestOTPCodeToSMS(@RequestParam("phoneNumber") phoneNumber: String): ResponseEntity<Any> {
        return try {

            val codeOTP = generateOTP()
            val message = "El Código de Verificación para ud es $codeOTP"



            if (smsBusiness!!.sendSMS(phoneNumber,message)) {
                ResponseEntity(codeOTP, HttpStatus.OK)
            } else {
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }



        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getAllUsers")
    fun getAllUsers():ResponseEntity<List<Usuario>>{
        return try {
            ResponseEntity(usuarioBusiness!!.getAllUsers(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getAllUsersPaginated")
    fun getAllUsersPaginated(pageable: Pageable):ResponseEntity<Page<Usuario>>{ //@RequestParam("pageable")
        return try {
            ResponseEntity(usuarioBusiness!!.getAllUsersPaginated(pageable),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/searchUsersPaginated")
    fun searchUsersPaginated(pageable: Pageable,@RequestParam("search") search:String?):ResponseEntity<Page<Usuario>>{ //@RequestParam("pageable")
        return try {
            ResponseEntity(usuarioBusiness!!.searchAllUsersPaginated(search?:"",pageable),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/searchUsersPaginatedWithFilter")
    fun searchUsersPaginatedWithFilter(pageable: Pageable,@RequestBody userFilterOptions: UserFilterOptions?):ResponseEntity<Page<Usuario>>{ //@RequestParam("pageable") ,@RequestParam("search") search:String?

        return try {
            ResponseEntity(usuarioBusiness!!.searchUsersPaginatedWithFilter(userFilterOptions?:UserFilterOptions(),pageable),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getUserById={id}")
    fun getUserById(@PathVariable("id") idUsuario: String  ):ResponseEntity<Usuario> {
        return try {
            ResponseEntity(usuarioBusiness!!.getUserById(idUsuario),HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PostMapping("/addUser")
    fun addUser(@RequestBody usuario: Usuario): ResponseEntity<Any>{
        return try {



           usuario.fechaDeRegistro = getServerLocalDate()
           usuario.habilitado = true


            //Devuelve solo el Header con el ID del usuario creado
//            usuarioBusiness!!.addUser(usuario)
//            val responseHeader = org.springframework.http.HttpHeaders()
//            responseHeader.set("id",  usuario.id)
//            ResponseEntity(responseHeader,HttpStatus.CREATED)

            //Devuelve el Body completo del Usuario Creado
            ResponseEntity( usuarioBusiness!!.addUser(usuario),HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/updateUser")
    fun updateUser(@RequestBody usuario: Usuario): ResponseEntity<Any>{

        return try {
            ResponseEntity( usuarioBusiness!!.updateUser(usuario),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @PutMapping("/updateUserLocationById")
    fun updateUserLocationById(@RequestParam idUsuario: String,@RequestBody ubicacion: Ubicacion): ResponseEntity<Any>{
        return try {
            ResponseEntity( usuarioBusiness!!.updateUserLocationById(idUsuario,ubicacion ),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @DeleteMapping("/deleteUserById={id}")
    fun deleteUserById(@PathVariable("id") idUsuario: String): ResponseEntity<Any>{
        return try {
            usuarioBusiness!!.deleteUserById(idUsuario)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @DeleteMapping("/deleteAllUsers")
    fun deleteAllUsers(): ResponseEntity<Any>{
        return try {
            usuarioBusiness!!.deleteAllUsers()

            val responseHeader = org.springframework.http.HttpHeaders()
            responseHeader.set("BORRADOS","SI")
            ResponseEntity(responseHeader,HttpStatus.CREATED)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("/countUsers")
    fun countUsers():String{
        return try {
            usuarioBusiness!!.countUsers().toString()

        }catch (e:Exception){
            "-1"
        }
    }



}