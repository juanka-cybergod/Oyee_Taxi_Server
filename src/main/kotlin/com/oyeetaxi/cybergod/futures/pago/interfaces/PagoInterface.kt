package com.oyeetaxi.cybergod.futures.pago.interfaces

import com.oyeetaxi.cybergod.futures.pago.models.Pago

interface PagoInterface {

    fun addPayment(pago: Pago):Pago
    fun updatePayment(pago: Pago):Pago
    fun isVehiclePaid(vehicleId: String):Boolean



//    fun getAllUsers(): List<Usuario>
//    fun getAllUsersPaginated(pageable: Pageable):Page<Usuario>
//    fun searchAllUsersPaginated(search:String,pageable: Pageable):Page<Usuario>
//    fun searchUsersPaginatedWithFilter(userFilterOptions: UserFilterOptions, pageable: Pageable):Page<Usuario>
//    fun getUserById(idUsuario: String): Usuario
//    fun addUser(usuario: Usuario): Usuario
//    fun updateUser(usuario: Usuario): Usuario
//    fun deleteUserById(idUsuario: String)
//    fun deleteAllUsers()
//    fun countUsers():Long
//    fun findUserByPhoneNumber(phoneNumber: String): Usuario?
//    fun updateUserLocationById(idUsuario: String, ubicacion: Ubicacion): Boolean
//
//    fun getUserByEmail(email: String): Usuario?
//    fun getUserEmailByPhoneNumber(phoneNumber: String): String?
//
//    fun emailExist(email: String): Boolean

}
