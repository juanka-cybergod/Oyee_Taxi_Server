package com.oyeetaxi.cybergod.configuration


import com.oyeetaxi.cybergod.futures.vehiculo.services.VehiculoService
import com.oyeetaxi.cybergod.utils.GlobalVariables.availableVehiclesListGlobal
import com.oyeetaxi.cybergod.utils.GlobalVariables.updateAvailableVehiclesRate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


@Configuration
//@EnableScheduling    // TODO HABILITAR ESTA LINEA PARA Q FUNCIONE
//@EnableAsync
class SchedulerConfiguration(
    @Autowired private val vehiculoService: VehiculoService
) {

    private var currentTime:Long = 0
    private var updateRate: Long = updateAvailableVehiclesRate



    //@Async
    @Scheduled(initialDelay = 2,fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    fun updateAviablesVehiclesList(){
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


        vehiculoService.getAviableVehicles()


    }

    private fun updateAviablesVehiclesListNow() {

        println("\n")

        vehiculoService.getAviableVehicles().let {

            availableVehiclesListGlobal = it

            it.map { vehiculo ->
                println( "${vehiculo.tipoVehiculo?.tipoVehiculo} -> ${vehiculo.usuario?.nombre}" )
            }

        }

        println("\n")


    }


}