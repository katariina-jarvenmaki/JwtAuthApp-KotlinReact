package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/public")
class PublicController(
    private val jwtUtils: JwtUtils
) {
    @GetMapping("/token")
    fun getToken(): ResponseEntity<Map<String, String>> {
        val token = jwtUtils.generate("publicUser")  // You can use a placeholder or real username
        return ResponseEntity.ok(mapOf("token" to token))
    }
}
