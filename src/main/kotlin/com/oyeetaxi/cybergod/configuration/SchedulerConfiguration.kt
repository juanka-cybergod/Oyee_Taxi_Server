package com.oyeetaxi.cybergod.configuration


import com.oyeetaxi.cybergod.futures.configuracion.repositories.ConfiguracionRepository
import com.oyeetaxi.cybergod.futures.share.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.repositories.UsuarioRepository
import com.oyeetaxi.cybergod.futures.usuario.services.UsuarioService
import com.oyeetaxi.cybergod.futures.vehiculo.services.VehiculoService
import com.oyeetaxi.cybergod.utils.Constants
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_CONFIG
import com.oyeetaxi.cybergod.utils.Constants.DEFAULT_updateAvailableVehiclesRate
import com.oyeetaxi.cybergod.utils.GlobalVariables.updateAvailableVehiclesRate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors


@Configuration
@EnableScheduling    // TODO HABILITAR ESTA LINEA PARA Q FUNCIONE
@EnableAsync
class SchedulerConfiguration(
    @Autowired private val vehiculoService: VehiculoService,
    @Autowired private val usuarioService: UsuarioService,
    @Autowired private val usuarioRepository: UsuarioRepository,
    @Autowired private val configuracionRepository: ConfiguracionRepository,


) {

    private var currentTime:Long = 0

    init {

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
            latitud = 22.4067149,
            longitud = -79.9651391,
            rotacion = 30,
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
            latitud = 22.3027049,
            longitud = -79.8601691,
            rotacion = 40,
        )
        val ubicacionMotoneta : Ubicacion = Ubicacion(
            latitud = 22.5037049,
            longitud = -79.8601691,
            rotacion = 16,
        )


        val listaUsuarios =  usuarioRepository.findAll()


        listaUsuarios
            .filter { usuario -> usuario.conductor==true } //&& usuario.superAdministrador != true && usuario.administrador != true
            .forEach { usuario ->

                when (usuario.id) {
                    "id_usuario_cliente"-> usuario.ubicacion= ubicacionCliente
                    "id_usuario_taxi"-> usuario.ubicacion= ubicacionAuto
                    "id_usuario_van"-> usuario.ubicacion= ubicacionVan
                    "id_usuario_guagua"-> usuario.ubicacion= ubicacionGuagua
                    "id_usuario_camion_carga"-> usuario.ubicacion= ubicacionCamioCarga
                    "id_usuario_camion_pasaje"-> usuario.ubicacion= ubicacionCamioPasaje
                    "id_usuario_moto"-> usuario.ubicacion= ubicacionMoto
                    "id_usuario_motoneta"-> usuario.ubicacion= ubicacionMotoneta
                    "id_usuario_motorina"-> usuario.ubicacion= ubicacionMotorina
                }
                usuarioRepository.save(usuario)
            }





    }


    private var log : Double = 00.0020000
    private var lat : Double = 00.0020000



    @Scheduled(initialDelay = 1,fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    private fun getAndRefreshUpdateTimeRate(){

        currentTime++

        if (currentTime.mod(updateAvailableVehiclesRate).toInt() == 0) {

            currentTime = 0
            val optional = configuracionRepository.findById(DEFAULT_CONFIG)

            updateAvailableVehiclesRate = if (optional.isPresent) {
                optional.get().actualizacionVehiculosDisponibles ?: DEFAULT_updateAvailableVehiclesRate
            } else {DEFAULT_updateAvailableVehiclesRate}

           // println("updateAvailableVehiclesRate = $updateAvailableVehiclesRate")

            moveVehiclesRomdomley()

        }

    }


    //@Async
//    @Scheduled(initialDelay = 10,fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    fun moveVehiclesRomdomley(){

        val listaUsuarios =  usuarioRepository.findAll()

        val min = 00.0000000
        val max = 00.0055555

            listaUsuarios
                .filter { usuario -> usuario.conductor==true } //&& usuario.superAdministrador != true && usuario.administrador != true
                .forEach { usuario ->



                    val ubicacion = usuario.ubicacion!!

                    val a = DecimalFormat("00").format(Random().nextInt(99)).toInt()
                    if ( a.mod(2) == 0 ) {


                        if ( DecimalFormat("00").format(Random().nextInt(99)).toInt().mod(2) == 0 ) {
                            ubicacion.longitud = ubicacion.longitud!! - log
                            ubicacion.latitud = ubicacion.latitud!! - lat

                            log +=  ThreadLocalRandom.current().nextDouble(min, max)
                            lat += ThreadLocalRandom.current().nextDouble(min, max)
                        } else {
                            ubicacion.longitud = ubicacion.longitud!! + log
                            ubicacion.latitud = ubicacion.latitud!! + lat

                            log -=  ThreadLocalRandom.current().nextDouble(min, max)
                            lat -= ThreadLocalRandom.current().nextDouble(min, max)
                        }


                    } else {

                        if ( DecimalFormat("00").format(Random().nextInt(99)).toInt().mod(2) == 0 ) {
                            ubicacion.longitud = ubicacion.longitud!! + log
                            ubicacion.latitud = ubicacion.latitud!! - lat

                            log +=  ThreadLocalRandom.current().nextDouble(min, max)
                            lat += ThreadLocalRandom.current().nextDouble(min, max)
                        } else {
                            ubicacion.longitud = ubicacion.longitud!! - log
                            ubicacion.latitud = ubicacion.latitud!! + lat

                            log -=  ThreadLocalRandom.current().nextDouble(min, max)
                            lat -= ThreadLocalRandom.current().nextDouble(min, max)
                        }

                    }


                    usuario.ubicacion = ubicacion
                    usuarioRepository.save(usuario)


                }





    }






//    //@Async
//    @Scheduled(initialDelay = 2,fixedRate = 5, timeUnit = TimeUnit.SECONDS)
//    fun moveVehiclesRomdomley(){
//        val listaUsuarios =  usuarioRepository.findAll()
//
//        val min = 00.0000000
//        val max = 00.0055555
//
//            listaUsuarios
//                .filter { usuario -> usuario.conductor==true } //&& usuario.superAdministrador != true && usuario.administrador != true
//                .forEach { usuario ->
//
//
//
//                    val ubicacion = usuario.ubicacion!!
//
//                    val a = DecimalFormat("00").format(Random().nextInt(99)).toInt()
//                    if ( a.mod(2) == 0 ) {
//
//
//                        if ( DecimalFormat("00").format(Random().nextInt(99)).toInt().mod(2) == 0 ) {
//                            ubicacion.longitud = ubicacion.longitud!! - log
//                            ubicacion.latitud = ubicacion.latitud!! - lat
//
//                            log +=  ThreadLocalRandom.current().nextDouble(min, max)
//                            lat += ThreadLocalRandom.current().nextDouble(min, max)
//                        } else {
//                            ubicacion.longitud = ubicacion.longitud!! + log
//                            ubicacion.latitud = ubicacion.latitud!! + lat
//
//                            log -=  ThreadLocalRandom.current().nextDouble(min, max)
//                            lat -= ThreadLocalRandom.current().nextDouble(min, max)
//                        }
//
//
//                    } else {
//
//                        if ( DecimalFormat("00").format(Random().nextInt(99)).toInt().mod(2) == 0 ) {
//                            ubicacion.longitud = ubicacion.longitud!! + log
//                            ubicacion.latitud = ubicacion.latitud!! - lat
//
//                            log +=  ThreadLocalRandom.current().nextDouble(min, max)
//                            lat += ThreadLocalRandom.current().nextDouble(min, max)
//                        } else {
//                            ubicacion.longitud = ubicacion.longitud!! - log
//                            ubicacion.latitud = ubicacion.latitud!! + lat
//
//                            log -=  ThreadLocalRandom.current().nextDouble(min, max)
//                            lat -= ThreadLocalRandom.current().nextDouble(min, max)
//                        }
//
//                    }
//
//
//                    usuario.ubicacion = ubicacion
//                    usuarioRepository.save(usuario)
//
//
//                }
//
//
//
//
//
//    }




//    //@Async
//    @Scheduled(initialDelay = 2,fixedRate = 1, timeUnit = TimeUnit.SECONDS)
//    fun updateAviablesVehiclesList(){
//        val time = LocalDateTime.now()
//        val myformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") //LocalDate.parse("28-10-1989",myformatter)
//        val stringTime = time.format(myformatter)
//
//        println("stringTime + currentTime = $currentTime")
//
//        if (currentTime==updateRate) {
//            currentTime = 0
//            updateAviablesVehiclesListNow()
//        } else {
//            currentTime++
//        }
//
//
//        vehiculoService.getAviableVehicles()
//
//
//    }

//    private fun updateAviablesVehiclesListNow() {
//
//        println("\n")
//
//        vehiculoService.getAviableVehicles().let {
//
//            availableVehiclesListGlobal = it
//
//            it.map { vehiculo ->
//                println( "${vehiculo.tipoVehiculo?.tipoVehiculo} -> ${vehiculo.usuario?.nombre}" )
//            }
//
//        }
//
//        println("\n")
//
//
//    }


}