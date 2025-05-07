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
import org.springframework.stereotype.Service
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    fun authenticate(request: AuthRequest): String {
        val user = userRepository.findByUsername(request.username)
            ?: throw UsernameNotFoundException("User not found")

        if (user.password != request.password) {
            throw BadCredentialsException("Invalid password")
        }

        return jwtUtils.generateToken(user.username)
    }
}