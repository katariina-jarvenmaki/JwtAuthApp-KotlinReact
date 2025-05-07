package com.katariinatuulia.backend

import com.katariinatuulia.backend.jwt_auth.JwtUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.concurrent.TimeUnit

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    private lateinit var validToken: String
    private val testUsername = "testUser"

    @BeforeEach
    fun setUp() {
        validToken = jwtUtils.generate(testUsername)
    }

    @Test
    fun `generate should return a valid JWT token`() {
        // Given
        val username = "testUser"

        // When
        val token = jwtUtils.generate(username)

        // Then
        assertNotNull(token)
        assertEquals(3, token.split(".").size) // JWT has 3 parts
    }

    @Test
    fun `validate should return true for a valid token`() {
        assertTrue(jwtUtils.validate(validToken))
    }

    @Test
    fun `validate should return false for an invalid token`() {
        assertFalse(jwtUtils.validate("invalid.token.string"))
    }

    @Test
    fun `username should return correct subject from token`() {
        assertEquals(testUsername, jwtUtils.username(validToken))
    }

    @Test
    fun `username should throw exception for invalid token`() {
        assertThrows<Exception> {
            jwtUtils.username("invalid.token.string")
        }
    }

    @Test
    fun `validate should return false for an expired token`() {
        // Given
        val now = System.currentTimeMillis()
        val expiredToken = Jwts.builder()
            .setSubject(testUsername)
            .setIssuedAt(Date(now - 2000))
            .setExpiration(Date(now - 1000)) // Expired 1 second ago
            .signWith(jwtUtils.signer)
            .compact()

        // When & Then
        assertFalse(jwtUtils.validate(expiredToken))
    }

    @Test
    fun `username should throw for expired token`() {
        // Given
        val now = System.currentTimeMillis()
        val expiredToken = Jwts.builder()
            .setSubject(testUsername)
            .setIssuedAt(Date(now - 2000))
            .setExpiration(Date(now - 1000)) // Expired 1 second ago
            .signWith(jwtUtils.signer)
            .compact()

        // When & Then
        assertThrows<Exception> {
            jwtUtils.username(expiredToken)
        }
    }

    @Test
    fun `generated token should have valid expiration`() {
        // Given
        val token = jwtUtils.generate(testUsername)
        val toleranceMs = 1000 // 1 second tolerance

        // When
        val claims = Jwts.parserBuilder()
            .setSigningKey(jwtUtils.signer)
            .build()
            .parseClaimsJws(token)
            .body

        val expiration = claims.expiration.time
        val expectedExpiration = System.currentTimeMillis() + 3600000 // 1 hour

        // Then
        assertTrue(expiration in (expectedExpiration - toleranceMs)..(expectedExpiration + toleranceMs))
    }
}