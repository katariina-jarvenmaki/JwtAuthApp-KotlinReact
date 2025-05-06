package com.katariinatuulia.backend.jwt_auth

import com.katariinatuulia.backend.jwt_auth.UserRepository
import com.katariinatuulia.backend.jwt_auth.User
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Component
class DataInitializer(
    private val userRepository: UserRepository
) {
    private val encoder = BCryptPasswordEncoder()

    @PostConstruct
    fun init() {
        val existingUser = userRepository.findByUsername("admin")
        if (existingUser == null) {
            val hashedPassword = encoder.encode("admin123")
            val newUser = User(username = "admin", password = hashedPassword)
            userRepository.save(newUser)
            println("Default admin user created")
        }
    }
}
