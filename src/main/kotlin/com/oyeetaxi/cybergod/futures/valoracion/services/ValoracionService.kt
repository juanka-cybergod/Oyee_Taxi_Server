package com.oyeetaxi.cybergod.futures.valoracion.services


import com.oyeetaxi.cybergod.exceptions.BusinessException
import com.oyeetaxi.cybergod.exceptions.NotFoundException
import com.oyeetaxi.cybergod.futures.valoracion.interfaces.ValoracionInterface
import com.oyeetaxi.cybergod.futures.valoracion.models.Valoracion
import com.oyeetaxi.cybergod.futures.valoracion.repositories.ValoracionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class ValoracionService : ValoracionInterface {

    //private var LOGGER = LoggerFactory.getLogger(ValoracionService::class.java)

    @Autowired
    val valoracionRepository: ValoracionRepository? = null


    @Throws(BusinessException::class, NotFoundException::class)
    override fun addUpdateValoration(valoracion: Valoracion): Valoracion {

       return  try {

            if (valoracion.id.isNullOrEmpty()) {
                //insertar
                addValoracion(valoracion)
            } else {
                updateValoracion(valoracion)
            }

        } catch (e:Exception) {
            throw BusinessException(e.message)
        }


    }



    override fun getValorationAverageByUserId(userId: String): Float? {

        var totalValoraciones : Float = 0f
        var sumaValoraciones : Float = 0f
        var valoracionFinal :Float? = null

        val optionalLstaValoraciones = valoracionRepository!!.findValoracionesByUserId(userId)

        //LOGGER.info("userId = $userId")
        //LOGGER.info("valoracionEncontrada = $optionalLstaValoraciones")

        if (optionalLstaValoraciones.isPresent && optionalLstaValoraciones.get().isNotEmpty()) {


              optionalLstaValoraciones.get().forEach { valoracionItem ->

                  valoracionItem.valoracion?.let { value ->

                      sumaValoraciones += value
                      totalValoraciones++


                  }

              }

            if (totalValoraciones > 0) {
                valoracionFinal = sumaValoraciones / totalValoraciones
            }


        } else {
            //LOGGER.info("El Usuario con Id $userId no tiene valoraciones aun")
           // throw NotFoundException("El Usuario con Id $userId no tiene valoraciones aun")
        }

        return valoracionFinal

    }


    @Throws(NotFoundException::class)
    override fun getValorationByUsersId(idUsuarioValora:String, idUsuarioValorado:String) : Valoracion {

        val optionalLstaValoraciones = valoracionRepository!!.findValoracionByUsersId(
            idUsuarioValora,
            idUsuarioValorado
        )


        if (optionalLstaValoraciones.isPresent && optionalLstaValoraciones.get().isNotEmpty()) {

            optionalLstaValoraciones.get().forEach { valoracionItem ->
                //LOGGER.info("valoracionEncontrada = $valoracionItem")
            }


            return optionalLstaValoraciones.get().last()
        } else {
            throw NotFoundException("Valoracion No Encontrada")
        }



    }

    @Throws(BusinessException::class)
    private fun addValoracion(valoracion: Valoracion) : Valoracion {
        try {
            return valoracionRepository!!.insert(valoracion)
        }catch (e:Exception) {
            throw BusinessException(e.message)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    private fun updateValoracion(valoracion: Valoracion) : Valoracion {
        val optional: Optional<Valoracion>
        var valoracionActualizada : Valoracion = valoracion

        valoracion.id?.let { id ->

            try {
                optional = valoracionRepository!!.findById(id)
            }catch (e:Exception) {
                throw BusinessException(e.message)
            }

            if (!optional.isPresent){
                throw NotFoundException("Valoracion con ID $id No Encontrada")
            } else {

                val valoracionModificar : Valoracion = optional.get()

                valoracion.valoracion?.let { valoracionModificar.valoracion = it}
                valoracion.opinion?.let { valoracionModificar.opinion = it}

                try {
                    valoracionActualizada = valoracionRepository!!.save(valoracionModificar)
                }catch (e:Exception){
                    throw BusinessException(e.message)
                }


            }



        }

        return valoracionActualizada



    }




}

