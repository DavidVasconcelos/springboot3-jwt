package com.springjwt.security.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    @Value("\${jwt.secret-key}") val secretKey: String,
    @Value("\${jwt.expiration-time}") val jwtExpirationTime: Int
) {

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    fun generateToken(userDetails: UserDetails) = generateToken(mapOf(), userDetails)

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String = Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.username)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + jwtExpirationTime))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact()

    fun isTokenValid(token: String, userDetails: UserDetails) =
        (extractUsername(token) == userDetails.username && !isTokenExpired(token))

    private fun isTokenExpired(token: String) = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String) = extractClaim(token, Claims::getExpiration)

    private fun extractAllClaims(token: String) = Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .body

    private fun getSignInKey() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
}
