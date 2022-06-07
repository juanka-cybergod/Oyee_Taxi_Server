package com.oyeetaxi.cybergod.futures.vehiculo.controller



import com.oyeetaxi.cybergod.futures.share.controller.BaseRestController
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.vehiculo.models.response.VehiculoResponse
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_VEHICULOS
import com.oyeetaxi.cybergod.utils.Utils.getServerLocalDate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.oyeetaxi.cybergod.futures.vehiculo.models.Vehiculo
import com.oyeetaxi.cybergod.futures.vehiculo.models.requestFilter.VehicleFilterOptions
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


@RestController
@RequestMapping(URL_BASE_VEHICULOS)
class VehiculoRestController: BaseRestController() {



    @GetMapping("/getAvailableVehicles")
    fun getAvailableVehicles():ResponseEntity<List<VehiculoResponse>>{

        val listaVehiculosResponse : MutableCollection<VehiculoResponse> = ArrayList()

        vehiculoService.getAviableVehicles().let { listaVehiculosDisponibles ->

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

        vehiculoService.getActiveVehicleByUserId(idUsuario)?.let { vehiculo ->

            vehiculoResponse = VehiculoResponse(
                id = vehiculo.id,
                usuario = null,
                tipoVehiculo = tipoVehiculoService.getVehicleTypeById(vehiculo.tipoVehiculo!!),
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


        vehiculoService.getAllVehiclesFromUserId(idUsuario).let { listaVehiculosDeUsuario ->

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

        vehiculoService.getAllVehiclesFromUserId(idUsuario).let { listaVehiculosDeUsuario ->

            listaVehiculosDeUsuario.forEach { vehiculo ->
                //LOGGER.info(vehiculo.id.toString())

                when (idVehiculo) {
                        vehiculo.id -> {
                            vehiculoActivo =  vehiculoService.setActiveVehicle(vehiculo,true)
                        }
                        else -> {
                            vehiculoService.setActiveVehicle(vehiculo,false)
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
            ResponseEntity(vehiculoService.getAllVehicles(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/searchVehiclesPaginatedWithFilter")
    fun searchVehiclesPaginatedWithFilter(pageable: Pageable, @RequestBody vehicleFilterOptions: VehicleFilterOptions?):ResponseEntity<Page<VehiculoResponse>>{ //@RequestParam("pageable") ,@RequestParam("search") search:String?

        return try {
            ResponseEntity(vehiculoService.searchVehiclesPaginatedWithFilter(vehicleFilterOptions?: VehicleFilterOptions(),pageable),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }



    @GetMapping("/getVehicleById={id}")
    fun getVehicleById(@PathVariable("id") idVehiculo: String  ):ResponseEntity<Vehiculo> {
        return try {
            ResponseEntity(vehiculoService.getVehicleById(idVehiculo),HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PostMapping("/addVehicle")
    fun addVehicle(@RequestBody vehiculo: Vehiculo): ResponseEntity<Any>{

        vehiculo.fechaDeRegistro = getServerLocalDate()

        setUserConductorById(vehiculo.idUsuario)

        return try {
            ResponseEntity(vehiculoService.addVehicle(vehiculo),HttpStatus.CREATED)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }


    }

    @PutMapping("/updateVehicle")
    fun updateVehicle(@RequestBody vehiculo: Vehiculo): ResponseEntity<Any>{

        return try {
            ResponseEntity(vehiculoService.updateVehicle(vehiculo),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @DeleteMapping("/deleteVehicleById={id}")
    fun deleteVehicleById(@PathVariable("id") idVehiculo: String): ResponseEntity<Any>{
        return try {
            vehiculoService.deleteVehicleById(idVehiculo)
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
            vehiculoService.deleteAllVehicles()

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
            vehiculoService.countVehicles().toString()

        }catch (e:Exception){
            "-1"
        }
    }





}