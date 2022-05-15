package com.oyeetaxi.cybergod.security


import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


import org.springframework.stereotype.Service


@Service
class ApiUserDetailsService: UserDetailsService {

    private var LOGGER = LoggerFactory.getLogger(ApiUserDetailsService::class.java)
    private val passGen : BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun loadUserByUsername(username: String?): UserDetails {



        val authorities  = HashSet<GrantedAuthority>()
        authorities.add( SimpleGrantedAuthority(ApiUser().roll))

        return User
            .withUsername(ApiUser().username)
            .password(passGen.encode( ApiUser().password))
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }


}