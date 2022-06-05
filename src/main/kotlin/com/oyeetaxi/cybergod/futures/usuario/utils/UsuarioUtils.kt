package com.oyeetaxi.cybergod.futures.usuario.utils

import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.valoracion.services.ValoracionService
import java.util.stream.Collectors

object UsuarioUtils {


    fun List<Usuario>.filterConductores(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter { usuario->
                param == usuario.conductor
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterDeshabilitados(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter { usuario->
                param != usuario.habilitado
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterAdministradores(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter { usuario->
                (param == usuario.administrador) || (param == usuario.superAdministrador)
            }
            .collect(Collectors.toList())
    }

    fun List<Usuario>.filterVerificacionesPendientes(param:Boolean):List<Usuario>{
        return this.stream()
            .parallel()
            .filter { usuario->
                (param) &&  (usuario.usuarioVerificacion?.verificado == false) &&  (!usuario.usuarioVerificacion?.identificacion.isNullOrEmpty()) && (!usuario.usuarioVerificacion?.imagenIdentificaionURL.isNullOrEmpty())
            }
            .collect(Collectors.toList())
    }



}