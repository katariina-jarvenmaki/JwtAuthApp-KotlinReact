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
import java.security.Key

@Component
class JwtUtils {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    val signer: Key
    get() = Keys.hmacShaKeyFor(secret.toByteArray())

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
        getClaims(token) // Let this throw exceptions
        true
    } catch (e: ExpiredJwtException) {
        false
    } catch (e: JwtException) {
        false
    }
    
    fun username(token: String): String = try {
        getClaims(token).subject
    } catch (e: JwtException) {
        throw Exception("Invalid token") // Wrap in generic exception
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(signer).build().parseClaimsJws(token).body
}