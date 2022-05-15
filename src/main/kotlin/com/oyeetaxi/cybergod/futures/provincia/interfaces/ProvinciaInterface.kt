package com.oyeetaxi.cybergod.futures.provincia.interfaces

import com.oyeetaxi.cybergod.futures.provincia.models.Provincia


interface ProvinciaInterface {

    fun getAllProvinces(): List<Provincia>
    fun getAvailableProvinces(): List<Provincia>

    fun getProvinceById(idProvincia: String): Provincia
    fun addProvince(provincia: Provincia): Provincia
    fun updateProvince(provincia: Provincia): Provincia
    fun deleteProvinceById(idProvincia: String)
    fun deleteAllProvinces()
    fun countProvinces():Long

}




