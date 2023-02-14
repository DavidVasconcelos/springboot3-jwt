package com.springjwt.security.config

import com.springjwt.security.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class ApplicationConfig(val repository: UserRepository) {

    @Bean
    fun userDetailsService() = UserDetailsService { username ->
        repository.findByEmail(username) ?: throw UsernameNotFoundException("User not found")
    }

}