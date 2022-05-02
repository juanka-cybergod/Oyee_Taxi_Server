package com.oyeetaxi.cybergod.Controladores



import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Modelos.*
import com.oyeetaxi.cybergod.Modelos.Respuestas.VehiculoResponse
import com.oyeetaxi.cybergod.Utiles.Constants.URL_BASE_VEHICULOS
import com.oyeetaxi.cybergod.Utiles.Utils.getServerLocalDate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_VEHICULOS)
class VehiculoRestController: BaseRestController() {



    @GetMapping("/getAvailableVehicles")
    fun getAvailableVehicles():ResponseEntity<List<VehiculoResponse>>{

        //Preparar la Respuesta
        val listaVehiculosResponse : MutableCollection<VehiculoResponse> = ArrayList()

        vehiculoBusiness!!.getAviableVehicles().let { listaVehiculosDisponibles ->

            listaVehiculosDisponibles.forEach { vehiculo ->
                listaVehiculosResponse.add(
                    convertVehicleToVehicleResponse(vehiculo)
                )
            }




        }


        return try {
            ResponseEntity(listaVehiculosResponse.toList(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getActiveVehicleByUserId={id}")
    fun getActiveVehicleByUserId(@PathVariable("id") idUsuario: String  ):ResponseEntity<VehiculoResponse>{

        var vehiculoResponse : VehiculoResponse? = null

        vehiculoBusiness!!.getActiveVehicleByUserId(idUsuario)?.let {  vehiculo ->

            vehiculoResponse = VehiculoResponse(
                id = vehiculo.id,
                usuario = null,
                tipoVehiculo = tipoVehiculosBusiness?.getVehicleTypeById(vehiculo.tipoVehiculo!!),
                marca = vehiculo.marca,
                modelo = vehiculo.modelo,
                ano = vehiculo.ano,
                capacidadPasajeros = vehiculo.capacidadPasajeros,
                capacidadEquipaje = vehiculo.capacidadEquipaje,
                capacidadCarga = vehiculo.capacidadCarga,
                disponible = vehiculo.disponible,
                climatizado = vehiculo.climatizado,
                fechaDeRegistro = vehiculo.fechaDeRegistro,
                imagenFrontalPublicaURL = vehiculo.imagenFrontalPublicaURL,
                vehiculoVerificacion = vehiculo.vehiculoVerificacion,
            )

        }


        return if (vehiculoResponse != null) {
            try {
                ResponseEntity(vehiculoResponse,HttpStatus.OK)
            }catch (e:Exception){
                ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }


    }


    @GetMapping("/getAllVehiclesFromUserId={id}")
    fun getAllVehiclesFromUserId(@PathVariable("id") idUsuario: String  ):ResponseEntity<List<VehiculoResponse>>{

        //Preparar la Respuesta
        val listaVehiculosResponse : MutableCollection<VehiculoResponse> = ArrayList()


        vehiculoBusiness!!.getAllVehiclesFromUserId(idUsuario).let {  listaVehiculosDeUsuario ->

            listaVehiculosDeUsuario.forEach { vehiculo ->
                listaVehiculosResponse.add(
                    convertVehicleToVehicleResponse(vehiculo)
                )
            }


        }


        return try {
            ResponseEntity(listaVehiculosResponse.toList(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }

    @GetMapping("/setActiveVehicleToUserId")
    fun setActiveVehicleToUserId(@RequestParam("idUsuario") idUsuario: String, @RequestParam("idVehiculo") idVehiculo: String):ResponseEntity<Any>{

        var vehiculoActivo = false

        vehiculoBusiness!!.getAllVehiclesFromUserId(idUsuario).let {  listaVehiculosDeUsuario ->

            listaVehiculosDeUsuario.forEach { vehiculo ->
                //LOGGER.info(vehiculo.id.toString())

                when (idVehiculo) {
                        vehiculo.id -> {
                            vehiculoActivo =  vehiculoBusiness!!.setActiveVehicle(vehiculo,true)
                        }
                        else -> {
                            vehiculoBusiness!!.setActiveVehicle(vehiculo,false)
                        }
                }
            }


        }


        return try {
            ResponseEntity(vehiculoActivo,HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }


    @GetMapping("/getAllVehicles")
    fun getAllVehicles():ResponseEntity<List<Vehiculo>>{
        return try {
            ResponseEntity(vehiculoBusiness!!.getAllVehicles(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getVehicleById={id}")
    fun getVehicleById(@PathVariable("id") idVehiculo: String  ):ResponseEntity<Vehiculo> {
        return try {
            ResponseEntity(vehiculoBusiness!!.getVehicleById(idVehiculo),HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PostMapping("/addVehicle")
    fun addVehicle(@RequestBody vehiculo: Vehiculo): ResponseEntity<Any>{

        vehiculo.fechaDeRegistro = getServerLocalDate()

        setUserConductorById(vehiculo.idUsuario)

        return try {
            ResponseEntity(vehiculoBusiness!!.addVehicle(vehiculo),HttpStatus.CREATED)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }

    @PutMapping("/updateVehicle")
    fun updateVehicle(@RequestBody vehiculo: Vehiculo): ResponseEntity<Any>{

        return try {
            ResponseEntity(vehiculoBusiness!!.updateVehicle(vehiculo),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @DeleteMapping("/deleteVehicleById={id}")
    fun deleteVehicleById(@PathVariable("id") idVehiculo: String): ResponseEntity<Any>{
        return try {
            vehiculoBusiness!!.deleteVehicleById(idVehiculo)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @DeleteMapping("/deleteAllVehicles")
    fun deleteAllVehicles(): ResponseEntity<Any>{
        return try {
            vehiculoBusiness!!.deleteAllVehicles()

            val responseHeader = org.springframework.http.HttpHeaders()
            responseHeader.set("BORRADOS","SI")
            ResponseEntity(responseHeader,HttpStatus.CREATED)
            //ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/countVehicles")
    fun countVehicles():String{
        return try {
            vehiculoBusiness!!.countVehicles().toString()

        }catch (e:Exception){
            "-1"
        }
    }





}