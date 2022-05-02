package com.oyeetaxi.cybergod.Interfaces

import com.oyeetaxi.cybergod.Modelos.Provincia


interface ProvinciaInterface {

    fun getAllProvinces(): List<Provincia>
    fun getProvinceById(idProvincia: String): Provincia
    fun addProvince(provincia: Provincia): Provincia
    fun updateProvince(provincia: Provincia): Provincia
    fun deleteProvinceById(idProvincia: String)
    fun deleteAllProvinces()
    fun countProvinces():Long

}




