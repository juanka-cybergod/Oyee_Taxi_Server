package com.oyeetaxi.cybergod


import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.type.UsuarioVerificacion
import com.oyeetaxi.cybergod.futures.vehiculo.models.VehiculoVerificacion
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import com.oyeetaxi.cybergod.utils.Utils.passwordEncode
import com.oyeetaxi.cybergod.futures.configuracion.models.Configuracion
import com.oyeetaxi.cybergod.futures.configuracion.models.balanceResponse.Balance
import com.oyeetaxi.cybergod.futures.configuracion.models.types.*
import com.oyeetaxi.cybergod.futures.configuracion.repositories.ConfiguracionRepository
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.oyeetaxi.cybergod.futures.provincia.models.Provincia
import com.oyeetaxi.cybergod.futures.provincia.repositories.ProvinciaRepository
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.models.TipoVehiculo
import com.oyeetaxi.cybergod.futures.tipo_vehiculo.repositories.TipoVehiculoRepository
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.repositories.VehiculoRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SpringBootApplication
class OyeeTaxiApplication:CommandLineRunner{

	/**Se Debe poner Autowired cada vez q se instancie un Repositorio dentro de Una Clasa*/
	@Autowired
	val usuarioRepository: UsuarioRepository? = null
	@Autowired
	val vehiculoRepository: VehiculoRepository? = null
	@Autowired
	val tipoVehiculoRepository: TipoVehiculoRepository? = null
	@Autowired
	val procinciaRepository: ProvinciaRepository? = null
	@Autowired
	val configuracionRepository: ConfiguracionRepository? = null


	fun populateBD(){
		val myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") //LocalDate.parse("28-10-1989",myformatter)


	 	val redesSociales : MutableList<RedSocial> = emptyList<RedSocial>().toMutableList()

		val visibles = true
		val facebook = RedSocial(
			disponible = visibles,
			nombre = "Facebook",
			ico = "ficheros/descarga/SOCIAL_FACEBOOK.PNG",
			url = "oyee.taxi.00",
			ayuda = "ID de su cuenta de facebook. Ej: Oyee.taxi.00"
		)
		val whatsapp = RedSocial(
			disponible = visibles,
			nombre = "WhatsApp",
			ico = "ficheros/descarga/SOCIAL_WHATSAPP.PNG",
			url = "+5353208579",
			ayuda = "Número completo incluyendo el código del país. Ej: +53 12345678"
			)
		val twitter = RedSocial(
			disponible = visibles,
			nombre = "Twitter",
			ico = "ficheros/descarga/SOCIAL_TWITTER.PNG",
			url = "@OyeeTaxi_oficial",
			ayuda = "ID de su cuenta personal comenzando con @ . Ej: @OyeeTaxi_oficial"
			)
		val instagram = RedSocial(
			disponible = visibles,
			nombre = "Instagram",
			ico = "ficheros/descarga/SOCIAL_INSTAGRAM.PNG",
			url = "Oyee.taxi.00",
			ayuda = "ID de su cuenta de instagram. Ej: Oyee.taxi.00"
		)
		val youtube = RedSocial(
			disponible = visibles,
			nombre = "YouTube",
			ico = "ficheros/descarga/SOCIAL_YOUTUBE.PNG",
			url = "OyeeTaxiOficial",
			ayuda = "Nombre de su canal de youtube. Ej: OyeeTaxiOficial"
		)
		val linkedin = RedSocial(
			disponible = visibles,
			nombre = "LinkedIn",
			ico = "ficheros/descarga/SOCIAL_LINKEDIN.PNG",
			url = "oyee-taxi-oficial",
			ayuda = "Nombre de su cuenta en linkedin. Ej: oyee-taxi-oficial"
		)
		with(redesSociales){
			add(whatsapp)
			add(facebook)
			add(instagram)
			add(youtube)
			add(twitter)
			add(linkedin)
		}


		val defaultConfiguration: Configuracion = Configuracion(
			DEFAULT_CONFIG,
			servidorActivoClientes = true,
			servidorActivoAdministradores = true,
			motivoServidorInactivoClientes = "El Sistema está en Mantenimiento",
			motivoServidorInactivoAdministradores = "El Sistema está Desactivado para Administradores por falta de pago",
			twilioConfiguracion = (TwilioConfiguracion(
				account_sid = "AC9e44b58cdd832019e03a8f045288b591",
				auth_token = "99e7fb6c2bbcba159a95c503871d4732",
				trial_number = "+19062890453",
				balance = Balance(
					currency = "USD",
					balance = 10.22,
					accountSid = null,
				),
				smsCost = 1.0, //costo por sms
			)),
			smsProvider = SmsProvider.TWILIO,
			emailConfiguracion = EmailConfiguracion(
				//serviceEmail = "oyeetaxioficial@gmail.com",
				host = "smtp.gmail.com",
				port = 587,
				username = "oyeetaxioficial@gmail.com",
				password = "djmanaxbxxkjzjer",
				properties_mail_transport_protocol = "smtp",
				properties_mail_smtp_auth = true,
				properties_mail_smtp_starttls_enable = true,
				properties_mail_debug = true,
			),
			updateConfiguracion = UpdateConfiguracion(
				available = true,
				version = 2,
				versionString = "1.1",
				fileSize = "20 MB",
				appURL = "ficheros/descarga/123.apk", //ficheros/descarga/123.apk
				packageName = "com.ubercab&hl=es_419&gl=US" ,
				forceUpdate = false,
				description = listOf<String>("- Solucionar errores conocidos","- Iconos modernizados","- Optimizacion de inicio","- Estabilidad de Conección")

			),
			socialConfiguracion = SocialConfiguracion(
				disponible = true,
				phone = "+5353208579",
				email = "oyeetaxioficial@gmail.com",
				web = "http://www.oyeetaxi.com",
				redesSociales = redesSociales.toList()
			)

		).also {
			configuracionRepository!!.save(it)
		}


		//



		val villaClara = Provincia(
			nombre = "Villa Clara",
			visible = true,

			ubicacion = Ubicacion(
				latitud = 22.4067049,
				longitud = -79.9651691,
				rotacion = 0,
				alturaMapa = 8,
			)
		).also {
			procinciaRepository!!.save(it)
		}
		val laHabana = Provincia(
			nombre = "La Habana",
			visible = true,
			ubicacion = Ubicacion(
				latitud = 23.113708,
				longitud = -82.366642,
				rotacion = 0,
				alturaMapa = 8,
			)
		).also {
			procinciaRepository!!.save(it)
		}
		val matanzas = Provincia(
			nombre = "Matanzas",
			visible = true,
			ubicacion = Ubicacion(
				latitud = 23.041986,
				longitud = -81.574046,
				rotacion = 0,
				alturaMapa = 8,
			)
		).also {
			procinciaRepository!!.save(it)
		}



		val verificacionDefault = UsuarioVerificacion(
			verificado = false,
			identificacion = null,
			imagenIdentificaionURL = null,
		)


		val ubicacionCliente : Ubicacion = Ubicacion(
			latitud = 22.6442956,
			longitud = -80.0462211,
			rotacion = 70,
		)

		val ubicacionAuto : Ubicacion = Ubicacion(
			latitud = 22.4067049,
			longitud = -79.9651691,
			rotacion = 30,
		)
		val ubicacionMoto : Ubicacion = Ubicacion(
			latitud = 22.3027049,
			longitud = -79.8601691,
			rotacion = 40,
		)
		val ubicacionCamioCarga : Ubicacion = Ubicacion(
			latitud = 22.5037049,
			longitud = -80.1621691,
			rotacion = 100,
		)
		val ubicacionCamioPasaje : Ubicacion = Ubicacion(
			latitud = 22.2077049,
			longitud = -79.9671691,
			rotacion = 90,
		)
		val ubicacionGuagua : Ubicacion = Ubicacion(
			latitud = 22.6097049,
			longitud = -79.9691691,
			rotacion = 70,
		)

		val ubicacionVan : Ubicacion = Ubicacion(
			latitud = 22.5037049,
			longitud = -79.9691691,
			rotacion = 60,
		)
		val ubicacionMotorina : Ubicacion = Ubicacion(
			latitud = 22.0067049,
			longitud = -79.4651691,
			rotacion = 95,
		)
		val ubicacionMotoneta : Ubicacion = Ubicacion(
			latitud = 22.5037049,
			longitud = -79.8601691,
			rotacion = 16,
		)



		val usuario_cliente = Usuario(
			id= "id_usuario_cliente",
			conductor = true,
			modoCondutor = true,
			nombre = "Juan Carlos",
			apellidos = "Cuellar Perez",
			imagenPerfilURL = "ficheros/descarga/frontal_cliente.JPG",
			telefonoMovil = "+5353208579",
			telefonoFijo = "42694575",
			correo = "juancarlostech891028@gmail.com",
			contrasena = passwordEncode("123"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("28-10-1989", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = true,
			superAdministrador = true,
			ubicacion = ubicacionCliente,
			usuarioVerificacion = UsuarioVerificacion(
				verificado = null,
				identificacion = "89102830106",
				imagenIdentificaionURL = "ficheros/descarga/ID_USUARIO_CLIENTE_USUARIO_VERIFICACION_20220603030431.JPG"
			),
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_taxi = Usuario(
			id= "id_usuario_taxi",
			conductor = true,
			modoCondutor = true,
			nombre = "Carlos",
			apellidos = "Gonzales Rivalta",
			imagenPerfilURL = "",
			telefonoMovil = "+5353208578",
			telefonoFijo = "42694575",
			correo = "carlosriva@gmail.com",
			contrasena = passwordEncode("123"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("02-04-1995", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionAuto,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_van = Usuario(
			id= "id_usuario_van",
			conductor = true,
			modoCondutor = true,
			nombre = "Piter",
			apellidos = "Alonso",
			imagenPerfilURL = "",
			telefonoMovil = "56857496",
			telefonoFijo = "42694575",
			correo = "piter_a@gmail.com",
			contrasena = passwordEncode("321"),
			provincia = matanzas,
			fechaDeNacimiento = LocalDate.parse("20-01-1990", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = true,
			superAdministrador = false,
			ubicacion = ubicacionVan,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_guagua = Usuario(
			id= "id_usuario_guagua",
			conductor = true,
			modoCondutor = true,
			nombre = "Pedro",
			apellidos = "Ramirez",
			imagenPerfilURL = "",
			telefonoMovil = "54857595",
			telefonoFijo = "42634851",
			correo = "pedrolovo@gmail.com",
			contrasena = passwordEncode("457541"),
			provincia = laHabana,
			fechaDeNacimiento = LocalDate.parse("20-01-1980", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionGuagua,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_camion_carga = Usuario(
			id= "id_usuario_camion_carga",
			conductor = true,
			modoCondutor = true,
			nombre = "Erich",
			apellidos = "Almirante Romualdo",
			imagenPerfilURL = "",
			telefonoMovil = "54781792",
			telefonoFijo = "",
			correo = "erich72@gmail.com",
			contrasena = passwordEncode("457541"),
			provincia = laHabana,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionCamioCarga,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_camion_pasaje = Usuario(
			id= "id_usuario_camion_pasaje",
			conductor = true,
			modoCondutor = true,
			nombre = "Kamilo",
			apellidos = "Quiro Martinez",
			imagenPerfilURL = "",
			telefonoMovil = "+5375859564",
			telefonoFijo = "",
			correo = "kkk@gmail.com",
			contrasena = passwordEncode("1234568"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = false,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionCamioPasaje,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_moto = Usuario(
			id= "id_usuario_moto",
			conductor = true,
			modoCondutor = true,
			nombre = "Cristofer",
			apellidos = "Perez Perez",
			imagenPerfilURL = "",
			telefonoMovil = "53758595",
			telefonoFijo = "",
			correo = "kris@gmail.com",
			contrasena = passwordEncode("1234568"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMoto,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_motoneta = Usuario(
			id= "id_usuario_motoneta",
			conductor = true,
			modoCondutor = true,
			nombre = "Alberto",
			apellidos = "Alejo Gomez",
			imagenPerfilURL = "",
			telefonoMovil = "53648257",
			telefonoFijo = "",
			correo = "alberto95@gmail.com",
			contrasena = passwordEncode("1234568"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotoneta,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}

		val usuario_motorina = Usuario(
			id= "id_usuario_motorina",
			conductor = true,
			modoCondutor = true,
			nombre = "Humberto",
			apellidos = "Hernandez Valdez",
			imagenPerfilURL = "",
			telefonoMovil = "+536341275",
			telefonoFijo = "",
			correo = "humbehh@gmail.com",
			contrasena = passwordEncode("1234568"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}


		Usuario(
			id= "id_usuario_borrar_01",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_01",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("qwe"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_02",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_02",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("asd"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_03",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_03",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("zxc"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_04",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_04",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("bnm"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_05",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_05",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("ljk"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_06",
			conductor = true,
			modoCondutor = true,
			nombre = "id_usuario_borrar_06",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("yui6"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_07",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_07",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("4953"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_08",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_08",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("fdglkn5  "),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_09",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_09",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode(" dbb"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_10",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_10",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("s1879"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_11",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_11",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode(" kp31"),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}
		Usuario(
			id= "id_usuario_borrar_12",
			conductor = false,
			modoCondutor = false,
			nombre = "id_usuario_borrar_12",
			apellidos = "",
			imagenPerfilURL = "",
			telefonoMovil = "",
			telefonoFijo = "",
			correo = "id_usuario_borrar@gmail.com",
			contrasena = passwordEncode("agasg sdvb "),
			provincia = villaClara,
			fechaDeNacimiento = LocalDate.parse("20-01-1972", myformatter),
			fechaDeRegistro = LocalDate.now(),
			habilitado = true,
			administrador = false,
			superAdministrador = false,
			ubicacion = ubicacionMotorina,
			usuarioVerificacion = verificacionDefault,
		).also {
			usuarioRepository!!.save(it)
		}




		val tipoVehiculo_Automovil = TipoVehiculo(
			tipoVehiculo = "Auto Móvil",
			descripcion  = "Automóvil de hasta 8 pasajeros para todo tipo de viajes con un buen nivel de confort",
			cuotaMensual = 200,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/AUTO_MOVIL_3.PNG",
		).also {
			tipoVehiculoRepository!!.save(it)
		}

		val tipoVehiculo_Autovan = TipoVehiculo(
			tipoVehiculo = "Auto Van",
			descripcion  = "Autos de mediano y gran tamaño de hasta 16 pasajeros para todo tipo de viajes",
			cuotaMensual = 100,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/AUTO_VAN_1.PNG",
		).also {
			tipoVehiculoRepository!!.save(it)
		}


		val tipoVehiculo_Autobus = TipoVehiculo(
			tipoVehiculo = "Auto Bús",
			descripcion  = "Ómnibus para la transportación de una gran número de pasajeros con un buen nivel de confort",
			cuotaMensual = 300,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/GUAGUA_1.PNG",

			).also {
			tipoVehiculoRepository!!.save(it)
		}



		val tipoVehiculo_Motoneta = TipoVehiculo(
			tipoVehiculo = "Motoneta",
			descripcion  = "Moto de combustión, de 3 ruedas para transportación de hasta 8 pasajeros a cortas y medianas distancias",
			cuotaMensual = 100,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/MOTONETA_1.PNG",

			).also {
			tipoVehiculoRepository!!.save(it)
		}

		val tipoVehiculo_Motocicleta = TipoVehiculo(
			tipoVehiculo = "Motocicleta",
			descripcion  = "Moto de combustión, de 2 ruedas para transportación de 1 o 2 pasajeros a cortas y medianas distancias",
			cuotaMensual = 100,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/MOTO.PNG",
		).also {
			tipoVehiculoRepository!!.save(it)
		}



		val tipoVehiculo_Motorina = TipoVehiculo(
			tipoVehiculo = "Motorina",
			descripcion  = "Motorina eléctrica de hasta 3 ruedas para el transporte de pasajeros a cortas distanciasa",
			cuotaMensual = 100,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = false,
			requiereVerification  = false,
			imagenVehiculoURL = "ficheros/descarga/MOTORINA_1.PNG",
		).also {
			tipoVehiculoRepository!!.save(it)
		}


		val tipoVehiculo_CamionPasaje = TipoVehiculo(
			tipoVehiculo = "Camión de Pasaje",
			descripcion  = "Vehículo de gran tamaño, acondicionado para la transportación de un gran número pasajeros",
			cuotaMensual = 300,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = true,
			transporteCarga  = true,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/CAMION_PASAJE_1.PNG",

			).also {
			tipoVehiculoRepository!!.save(it)
		}

		val tipoVehiculo_CamionCarga = TipoVehiculo(
			tipoVehiculo = "Camión de Carga",
			descripcion  = "Vahículo para el transporte de materiales pesados, productos de gran tamaño, artículos de del hogar etc. ",
			cuotaMensual = 200,
			seleccionable  = true,
			prioridadEnMapa = 0,
			transportePasajeros = false,
			transporteCarga  = true,
			requiereVerification  = true,
			imagenVehiculoURL = "ficheros/descarga/CAMION_CARGA.PNG",
		).also {
			tipoVehiculoRepository!!.save(it)
		}












		val vehiculoVerificacion = VehiculoVerificacion(
			verificado = false,
			circulacion = "",
			imagenCirculacionURL = "",
			matricula = "P525700",
		)

		val vehiculoAuto = Vehiculo(
			id = "id_auto",
			idUsuario = usuario_cliente.id,
			tipoVehiculo = tipoVehiculo_Automovil.tipoVehiculo,
			marca  = "Ford",
			modelo = "Skyline",
			ano= "1957",
			capacidadPasajeros = "7",
			capacidadEquipaje = "120",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = true,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			//imagenFrontalPublicaURL = "ficheros/descarga/id_auto_frontal.jpg",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoMoto = Vehiculo(
			id = "id_moto",
			idUsuario = usuario_moto.id,
			tipoVehiculo = tipoVehiculo_Motocicleta.tipoVehiculo,
			marca  = "Susuky",
			modelo = "AX 100",
			ano= "2002",
			capacidadPasajeros = "2",
			capacidadEquipaje = "10",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = false,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoCamionCarga = Vehiculo(
			id = "id_camion_carga",
			idUsuario = usuario_camion_carga.id,
			tipoVehiculo = tipoVehiculo_CamionCarga.tipoVehiculo,
			marca  = "B8",
			modelo = "Ruso",
			ano= "1970",
			capacidadPasajeros = "4",
			capacidadEquipaje = "10",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = false,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoCamionPasaje = Vehiculo(
			id = "id_camion_pasaje",
			idUsuario = usuario_camion_pasaje.id,
			tipoVehiculo = tipoVehiculo_CamionPasaje.tipoVehiculo,
			marca  = "Cheverolet",
			modelo = "Americano",
			ano= "1985",
			capacidadPasajeros = "64",
			capacidadEquipaje = "500",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = false,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoGuagua = Vehiculo(
			id = "id_guaga",
			idUsuario = usuario_guagua.id,
			tipoVehiculo = tipoVehiculo_Autobus.tipoVehiculo,
			marca  = "Youtong",
			modelo = "China",
			ano= "2008",
			capacidadPasajeros = "100",
			capacidadEquipaje = "500",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = true,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoVan = Vehiculo(
			id = "id_van",
			idUsuario = usuario_van.id,
			tipoVehiculo = tipoVehiculo_Autovan.tipoVehiculo,
			marca  = "Mercedes",
			modelo = "Sprinter",
			ano= "2013",
			capacidadPasajeros = "16",
			capacidadEquipaje = "120",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = true,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}
		val vehiculoMotorina = Vehiculo(
			id = "id_motorina",
			idUsuario = usuario_motorina.id,
			tipoVehiculo = tipoVehiculo_Motorina.tipoVehiculo,
			marca  = "Unico",
			modelo = "1000W",
			ano= "2019",
			capacidadPasajeros = "2",
			capacidadEquipaje = "120",
			capacidadCarga = "1000",
			activo = true,
			visible = true,//visible = true,
			habilitado = true,//habilitado = true,
			disponible = true,
			climatizado  = true,
			fechaDeRegistro = LocalDate.parse("01-01-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}

		val vehiculoMotoneta = Vehiculo(
			id = "id_motoneta",
			idUsuario = usuario_motoneta.id,
			tipoVehiculo = tipoVehiculo_Motoneta.tipoVehiculo,
			marca  = "Ural",
			modelo = "350 CC",
			ano= "1990",
			capacidadPasajeros = "8",
			capacidadEquipaje = "20",
			capacidadCarga = "0",
			activo = true,
			visible = true,
			habilitado = true,
			disponible = true,
			climatizado  = false,
			fechaDeRegistro = LocalDate.parse("10-03-2022", myformatter),
			imagenFrontalPublicaURL = "",
			vehiculoVerificacion = vehiculoVerificacion
		).also {
			vehiculoRepository!!.save(it)
		}








	}


	override fun run(vararg args: String?) {

		populateBD()

	}


}


fun main(args: Array<String>) {
	runApplication<OyeeTaxiApplication>(*args)
}





