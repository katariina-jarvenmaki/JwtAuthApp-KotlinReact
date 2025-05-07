package com.katariinatuulia.backend.jwt_auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils {
    private val secret = "mysecretkey" // Huom! Tämä kannattaa ladata esim. application.yml:stä

    fun generate(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun validate(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun username(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }
}
