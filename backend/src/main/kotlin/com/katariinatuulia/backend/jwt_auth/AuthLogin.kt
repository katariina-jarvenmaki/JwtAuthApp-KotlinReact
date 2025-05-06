package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.AuthRequest
import com.katariinatuulia.backend.jwt_auth.JwtUtils
import com.katariinatuulia.backend.jwt_auth.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@RestController
@RequestMapping("/api/auth")
class AuthLoginController(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
) {
    private val encoder = BCryptPasswordEncoder()

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
        val user = userRepository.findByUsername(authRequest.username)

        return if (user != null && encoder.matches(authRequest.password, user.password)) {
            val token = jwtUtils.generate(user.username)
            ResponseEntity.ok(mapOf("token" to token, "username" to user.username))
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }
    }
}
