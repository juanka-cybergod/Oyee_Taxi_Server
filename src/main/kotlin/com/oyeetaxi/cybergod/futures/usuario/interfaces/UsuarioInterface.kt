package com.oyeetaxi.cybergod.futures.usuario.interfaces

import com.oyeetaxi.cybergod.futures.base.models.Ubicacion
import com.oyeetaxi.cybergod.futures.usuario.models.Usuario

interface UsuarioInterface {

    fun getAllUsers(): List<Usuario>
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