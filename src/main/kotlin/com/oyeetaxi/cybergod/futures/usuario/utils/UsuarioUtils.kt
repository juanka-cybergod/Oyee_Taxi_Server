package com.oyeetaxi.cybergod.futures.usuario.utils

import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.valoracion.services.ValoracionService
import java.util.stream.Collectors

object UsuarioUtils {


    fun List<Usuario>.filterConductores(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter {
                param == it.conductor
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterDeshabilitados(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter {
                param != it.habilitado
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterAdministradores(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter {
                (param == it.administrador) || (param == it.superAdministrador)
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterVerificacionesPendientes(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter {
                (param) &&  (it.usuarioVerificacion?.verificado == false) &&  (!it.usuarioVerificacion?.identificacion.isNullOrEmpty()) && (!it.usuarioVerificacion?.imagenIdentificaionURL.isNullOrEmpty())
            }
            .collect(Collectors.toList())
    }



}