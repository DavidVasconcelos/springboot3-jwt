package com.springjwt.security.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(val service: AuthenticationService) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.register(request))

    @PostMapping("/authenticate")
    fun register(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> =
        ResponseEntity.ok(service.authenticate(request))
}
