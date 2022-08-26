package com.oyeetaxi.cybergod.futures.pago.services

import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.pago.interfaces.PagoInterface
import com.oyeetaxi.cybergod.futures.pago.models.Pago
import com.oyeetaxi.cybergod.futures.pago.models.type.EstadoPago
import com.oyeetaxi.cybergod.futures.pago.repositories.PagoRepository
import com.oyeetaxi.cybergod.utils.Utils
import com.oyeetaxi.cybergod.utils.Utils.getServerLocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class PagoService(
    @Autowired private val pagoRepository: PagoRepository
) : PagoInterface {


    @Throws(BusinessException::class)
    override fun addPayment(pago: Pago): Pago {
        try {
            return pagoRepository.insert(pago)
        } catch (e: Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updatePayment(pago: Pago): Pago {

        val optional: Optional<Pago>
        var pagoActualizado: Pago = pago

        pago.id?.let { id ->

            try {
                optional = pagoRepository.findById(id)
            } catch (e: Exception) {
                throw BusinessException(e.message)
            }

            if (!optional.isPresent) {
                throw NotFoundException("Pago con ID $id No Encontrado")
            } else {
                val pagoModificar: Pago = optional.get()

                pago.idUsuario?.let { pagoModificar.idUsuario = it }
                pago.idVehiculo?.let { pagoModificar.idVehiculo = it }
                pago.importe?.let { pagoModificar.importe = it }
                pago.diasPagados?.let { pagoModificar.diasPagados = it }
                pago.tarjetaCreditoOrigen?.let { pagoModificar.tarjetaCreditoOrigen = it}
                pago.tarjetaCreditoDestino?.let { pagoModificar.tarjetaCreditoDestino = it}
                pago.fechaCreado?.let { pagoModificar.fechaCreado = it }
                pago.fechaCompletado?.let { pagoModificar.fechaCompletado = it }
                pago.estado?.let { pagoModificar.estado = it }

                try {
                    pagoActualizado = pagoRepository.save(pagoModificar)
                } catch (e: Exception) {
                    throw BusinessException(e.message)

                }
            }

        }

        return pagoActualizado

    }


    override fun isVehiclePaid(vehicleId: String): Boolean {

        val optional: Optional<List<Pago>> = pagoRepository.getAllPaymentsByVehicleId(vehicleId)

        return if (!optional.isPresent || optional.get().isEmpty()) {
            //println("Vehiculo con ID $vehicleId No Tiene Pagos Realizados")
            false
        } else {

            val listaPagos = optional.get()
            //println("listaPagos = ${listaPagos.count()}")

            val ultimoPagoCompletado = listaPagos.last { it.estado == EstadoPago.COMPLETADO }
            //println("ultimoPagoCompletado = $ultimoPagoCompletado")

            val fechaActualServidor = getServerLocalDate()
            val fechaPagadoHasta =
                ultimoPagoCompletado.fechaCompletado?.plusDays(ultimoPagoCompletado.diasPagados ?: 30)
            //println("fechaPagadoHasta = $fechaPagadoHasta \nfechaActualServidor = $fechaActualServidor")

            fechaPagadoHasta?.let { pagadoHasta ->
                val diferenciaFechas = pagadoHasta.dayOfYear - fechaActualServidor.dayOfYear
                //println("diferenciaFechas = $diferenciaFechas")
            }


            val isPaid = try {
                fechaActualServidor.isBefore(fechaPagadoHasta)
            } catch (e: Exception) {
                false
            }

            //println("Vehiculo con ID $vehicleId Tiene Pago Activo = $isPaid ")

            isPaid


        }

    }


}