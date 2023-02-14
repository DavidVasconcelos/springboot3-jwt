package com.springjwt.security.auth

import com.springjwt.security.config.JwtService
import com.springjwt.security.user.Role
import com.springjwt.security.user.User
import com.springjwt.security.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    val repository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            userPassword = passwordEncoder.encode(request.password),
            role = Role.USER
        )
        repository.save(user)
        return AuthenticationResponse(token = jwtService.generateToken(user))
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))
        val user = repository.findByEmail(request.email) ?: throw UsernameNotFoundException("User not found")
        return AuthenticationResponse(token = jwtService.generateToken(user))
    }
}
