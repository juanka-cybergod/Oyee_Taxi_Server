package com.oyeetaxi.cybergod.security



import com.oyeetaxi.cybergod.utils.Constants.DOWNLOAD_FOLDER
import com.oyeetaxi.cybergod.utils.Constants.FILES_FOLDER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ApiSecurity : WebSecurityConfigurerAdapter() {

    @Autowired
    private val apiUserDetailsService = ApiUserDetailsService()
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()



    override fun configure(http: HttpSecurity) {
//        http
//            .csrf().disable()
//            .authorizeRequests()
//            //.antMatchers("$URL_BASE_CONFIGURACION/**")
//            .antMatchers("/")
//            .permitAll()
//            .anyRequest()
//            .authenticated()
//            .and()
//            .formLogin()


        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/$FILES_FOLDER/$DOWNLOAD_FOLDER/**","/").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            //.and()
            //.rememberMe().tokenValiditySeconds(TimeUnit.SECONDS.toSeconds(5).toInt())


    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
        //super.configure(auth)
    }


    @Bean
    fun authenticationProvider() : DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(bCryptPasswordEncoder)
        provider.setUserDetailsService(apiUserDetailsService)
        return provider

    }




}



