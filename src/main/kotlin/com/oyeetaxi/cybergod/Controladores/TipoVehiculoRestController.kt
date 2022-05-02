package com.oyeetaxi.cybergod.Controladores



import com.oyeetaxi.cybergod.Excepciones.BusinessException
import com.oyeetaxi.cybergod.Excepciones.NotFoundException
import com.oyeetaxi.cybergod.Modelos.TipoVehiculo
import com.oyeetaxi.cybergod.Servicios.TipoVehiculoService
import com.oyeetaxi.cybergod.Utiles.Constants.URL_BASE_TIPO_VEHICULOS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_TIPO_VEHICULOS)
class TipoVehiculoRestController {

    @Autowired
    val tipoVehiculosBusiness : TipoVehiculoService? = null



    @GetMapping("/getAllVehiclesType")
    fun getAllVehiclesType():ResponseEntity<List<TipoVehiculo>>{
        return try {
            ResponseEntity(tipoVehiculosBusiness!!.getAllVehiclesType(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @GetMapping("/getVehicleTypeById={id}")
    fun getVehicleTypeById(@PathVariable("id") idTipoVehiculo: String  ):ResponseEntity<TipoVehiculo> {
        return try {
            ResponseEntity(tipoVehiculosBusiness!!.getVehicleTypeById(idTipoVehiculo),HttpStatus.OK)
        }catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PostMapping("/addVehicleType")
    fun addVehicleType(@RequestBody tipoVehiculo: TipoVehiculo): ResponseEntity<Any>{
        return try {
            tipoVehiculosBusiness!!.addVehicleType(tipoVehiculo)
            val responseHeader = org.springframework.http.HttpHeaders()
            responseHeader.set("location",URL_BASE_TIPO_VEHICULOS + "/" + tipoVehiculo.tipoVehiculo)
            ResponseEntity(responseHeader,HttpStatus.CREATED)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/updateVehicleType")
    fun updateVehicleType(@RequestBody tipoVehiculo: TipoVehiculo): ResponseEntity<Any>{
        return try {
            tipoVehiculosBusiness!!.updateVehicleType(tipoVehiculo)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @DeleteMapping("/deleteVehicleTypeById={id}")
    fun deleteVehicleTypeById(@PathVariable("id") idTipoVehiculo: String): ResponseEntity<Any>{
        return try {
            tipoVehiculosBusiness!!.deleteVehicleTypeById(idTipoVehiculo)
            ResponseEntity(HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @DeleteMapping("/deleteAllVehiclesType")
    fun deleteAllVehiclesType(): ResponseEntity<Any>{
        return try {
            tipoVehiculosBusiness!!.deleteAllVehiclesType()

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

    @GetMapping("/countVehiclesType")
    fun countVehiclesType():String{
        return try {
            tipoVehiculosBusiness!!.countVehiclesType().toString()

        }catch (e:Exception){
            "-1"
        }
    }



}