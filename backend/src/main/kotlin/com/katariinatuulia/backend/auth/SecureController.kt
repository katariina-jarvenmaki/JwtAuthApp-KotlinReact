package com.katariinatuulia.backend.auth

//******************** IMPORTS ********************//

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.context.SecurityContextHolder

@RestController
@RequestMapping("/api/secure")
class SecureController {

    @GetMapping("/data")
    fun securedData(): Map<String, String> {
        val user = SecurityContextHolder.getContext().authentication.name
        return mapOf("message" to "Hello, $user! You have access.")
    }
}
