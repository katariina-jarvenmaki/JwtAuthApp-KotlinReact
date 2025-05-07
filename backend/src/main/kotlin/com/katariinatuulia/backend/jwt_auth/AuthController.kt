package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.AuthRequest
import com.katariinatuulia.backend.jwt_auth.JwtUtils
import com.katariinatuulia.backend.jwt_auth.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    private val encoder = BCryptPasswordEncoder()

    data class LoginRequest(val username: String, val password: String)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        val user = userRepository.findByUsername(request.username)
            ?: return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))

        if (!encoder.matches(request.password, user.password)) {
            return ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }

        val token = jwtUtils.generate(user.username)
        return ResponseEntity.ok(mapOf("token" to token, "username" to user.username))
    }
}
