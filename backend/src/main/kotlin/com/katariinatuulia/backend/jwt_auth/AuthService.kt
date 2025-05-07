package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.LoginRequest
import com.katariinatuulia.backend.jwt_auth.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.http.ResponseEntity

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    private val encoder = BCryptPasswordEncoder()

    fun authenticate(request: LoginRequest): ResponseEntity<Any> {
        val user = userRepository.findByUsername(request.username)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))

        if (!encoder.matches(request.password, user.password)) {
            return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }

        val token = jwtUtils.generate(user.username)
        return ResponseEntity.ok(mapOf("token" to token, "username" to user.username))
    }
}