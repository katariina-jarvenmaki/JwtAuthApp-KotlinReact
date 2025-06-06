package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.context.SecurityContextHolder

@RestController
@RequestMapping("/api/secure")
class SecureController {

    @GetMapping("/data")
    fun secureData(): ResponseEntity<String> {
        return ResponseEntity.ok("This is secured data")
    }
}
