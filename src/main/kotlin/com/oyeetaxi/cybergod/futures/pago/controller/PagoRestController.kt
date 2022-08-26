package com.oyeetaxi.cybergod.futures.pago.controller


import com.oyeetaxi.cybergod.futures.share.controller.BaseRestController
import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.futures.pago.models.Pago
import com.oyeetaxi.cybergod.futures.pago.models.type.EstadoPago
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.utils.Constants.URL_BASE_PAGOS
import com.oyeetaxi.cybergod.utils.Utils.getServerLocalDate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(URL_BASE_PAGOS)
class PagoRestController: BaseRestController() {


    @PostMapping("/addPayment")
    fun addPayment(@RequestBody pago: Pago): ResponseEntity<Any>{
        return try {
            pago.fechaCreado = getServerLocalDate().minusDays(1) //TODO Quitar minusDays()
            pago.fechaCompletado = getServerLocalDate()
            pago.estado = EstadoPago.PENDIENTE

            ResponseEntity( pagoService.addPayment(pago),HttpStatus.OK)

        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


    @PutMapping("/updatePayment")
    fun updatePayment(@RequestBody pago: Pago): ResponseEntity<Any>{

        return try {
            ResponseEntity( pagoService.updatePayment(pago),HttpStatus.OK)
        } catch (e: BusinessException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }



    @GetMapping("/isVehiclePaid")
    fun isVehiclePaid(@RequestParam("vehicleId") vehicleId: String): ResponseEntity<Boolean> {
        return try {
            ResponseEntity(pagoService.isVehiclePaid(vehicleId),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }


    @GetMapping("/getAllUsers")
    fun getAllUsers():ResponseEntity<List<Usuario>>{
        return try {
            ResponseEntity(usuarioService.getAllUsers(),HttpStatus.OK)
        }catch (e:Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }








}