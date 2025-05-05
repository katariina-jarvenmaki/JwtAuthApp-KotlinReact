package com.katariinatuulia.backend.auth

//******************** IMPORTS ********************//

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/public")
class AuthController(private val jwtUtils: JwtUtils) {

    @GetMapping("/token")
    fun getToken(): Map<String, String> {
        val token = jwtUtils.generate("testuser")
        return mapOf("token" to token)
    }
}

