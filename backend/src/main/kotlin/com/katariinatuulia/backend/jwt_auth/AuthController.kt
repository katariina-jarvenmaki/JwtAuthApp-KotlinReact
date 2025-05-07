package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequest): ResponseEntity<Any> {
        val token = authService.authenticate(request)
        return ResponseEntity.ok(mapOf("token" to token, "username" to request.username))
    }
}
