package com.springjwt.security.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTAuthenticationFilter(
    val jwtService: JwtService,
    val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authHeader.isNullOrEmpty() || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response)
        } else {
            SecurityContextHolder.getContext().authentication ?: fillContextWithAuthentication(authHeader, request)
            filterChain.doFilter(request, response)
        }
    }

    private fun fillContextWithAuthentication(authHeader: String, request: HttpServletRequest) {
        val jwt = authHeader.substring(TOKEN_INDEX)
        val userEmail = jwtService.extractUsername(jwt)
        val userDetails = userDetailsService.loadUserByUsername(userEmail)
        if (jwtService.isTokenValid(jwt, userDetails)) {
            val authToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        }
    }

    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_INDEX = 7
    }
}
