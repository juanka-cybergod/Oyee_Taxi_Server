package com.oyeetaxi.cybergod.futures.usuario.models.requestFilter

data class UserFilterOptions(
    var texto:String="",
    var condutores:Boolean?=null,
    var deshabilitados:Boolean?=null,
    var administradores:Boolean?=null,
    var verificacionesPendientes:Boolean?=null,
)
