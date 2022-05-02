package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.Valoracion


interface ValoracionInterface {


    fun addUpdateValoration(valoracion: Valoracion): Valoracion

    fun getValorationAverageByUserId(userId:String):Float?

    fun getValorationByUsersId(idUsuarioValora:String, idUsuarioValorado:String) : Valoracion

//    fun getAllValoraciones(): List<Valoracion>
//
//    fun getValoracionById(idProvincia: String): Provincia
//
//    fun updateProvince(provincia: Provincia): Provincia
//    fun deleteProvinceById(idProvincia: String)
//    fun deleteAllProvinces()
//    fun countProvinces():Long

}




