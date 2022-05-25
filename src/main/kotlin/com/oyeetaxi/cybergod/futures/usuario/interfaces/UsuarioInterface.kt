package com.oyeetaxi.cybergod.futures.usuario.interfaces

import com.oyeetaxi.cybergod.futures.base.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario
import com.oyeetaxi.cybergod.futures.usuario.models.requestFilter.UserFilterOptions
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UsuarioInterface {

    fun getAllUsers(): List<Usuario>
    fun getAllUsersPaginated(pageable: Pageable):Page<Usuario>
    fun searchAllUsersPaginated(search:String,pageable: Pageable):Page<Usuario>
    fun searchUsersPaginatedWithFilter(search:String, userFilterOptions: UserFilterOptions?, pageable: Pageable):Page<Usuario>
    fun getUserById(idUsuario: String): Usuario
    fun addUser(usuario: Usuario): Usuario
    fun updateUser(usuario: Usuario): Usuario
    fun deleteUserById(idUsuario: String)
    fun deleteAllUsers()
    fun countUsers():Long
    fun findUserByPhoneNumber(phoneNumber: String): Usuario?
    fun updateUserLocationById(idUsuario: String, ubicacion: Ubicacion): Boolean

    fun getUserByEmail(email: String): Usuario?
    fun getUserEmailByPhoneNumber(phoneNumber: String): String?

}