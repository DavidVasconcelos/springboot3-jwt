package com.springjwt.security.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)
