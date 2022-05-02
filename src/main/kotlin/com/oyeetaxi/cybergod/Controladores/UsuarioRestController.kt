package com.oyeetaxi.cybergod.Controladores


import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Modelos.Respuestas.LoginResponse
import com.oyeetaxi.cybergod.Modelos.Respuestas.RequestVerificationCodeResponse
import com.oyeetaxi.cybergod.Modelos.Respuestas.VehiculoResponse
import com.oyeetaxi.cybergod.Modelos.Ubicacion
import com.oyeetaxi.cybergod.Modelos.Usuario
import com.oyeetaxi.cybergod.Utiles.Constants
import com.oyeetaxi.cybergod.Utiles.Utils
import com.oyeetaxi.cybergod.Utiles.Utils.getServerLocalDate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(Constants.URL_BASE_USUARIOS)
class UsuarioRestController: BaseRestController() {


    @GetMapping("/requestVerificationCodeToRestorePassword")
    fun requestVerificationCodeToRestorePassword(@RequestParam("emailOrPhone") emailOrPhone: String): ResponseEntity<RequestVerificationCodeResponse> {

        val requestVerificationCodeResponse : RequestVerificationCodeResponse?
        var usuario:Usuario? = null
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
        var activeVehicleResponse : VehiculoResponse? = null
        var correctPasword  = false
        val messaje:String? = null



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

                    userFound = usuario


                }

            }



        val loginResponse = LoginResponse(
            usuarioEncontrado = usuarioEncontrado,
            contrasenaCorrecta = correctPasword,
            mensajeBienvenida = messaje,
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


    @GetMapping("/getAllUsers")
    fun getAllUsers():ResponseEntity<List<Usuario>>{
        return try {
            ResponseEntity(usuarioBusiness!!.getAllUsers(),HttpStatus.OK)
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
            ResponseEntity( usuarioBusiness!!.addUser(usuario),HttpStatus.CREATED)

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