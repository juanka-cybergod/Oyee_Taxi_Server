package com.oyeetaxi.cybergod.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class ApiUser(
    val userName:String ="API_CLIENT_ANDROID",
    val pass: String ="API_CLIENT_ANDROID_PASS",
    val roll: String = "API_CLIENT"
): UserDetails{





    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
       val  authority = SimpleGrantedAuthority("ROL")
       return Collections.singletonList(authority)

    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }







}
