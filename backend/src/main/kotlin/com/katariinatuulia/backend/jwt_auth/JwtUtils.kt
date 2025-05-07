package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtils {

    private val secret = "your-256-bit-secret-your-256-bit-secret"
    private val signer = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generate(username: String): String {
        val now = System.currentTimeMillis()
        val expiry = now + 1000 * 60 * 60 // 1h

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiry))
            .signWith(signer)
            .compact()
    }

    fun validate(token: String): Boolean = try {
        getClaims(token).expiration.after(Date())
    } catch (e: Exception) {
        false
    }

    fun username(token: String): String = getClaims(token).subject

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(signer).build().parseClaimsJws(token).body
}
