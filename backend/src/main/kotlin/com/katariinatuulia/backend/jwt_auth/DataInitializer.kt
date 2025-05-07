package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.UserRepository
import com.katariinatuulia.backend.jwt_auth.User
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.beans.factory.annotation.Value

@Component
class DataInitializer(
    private val userRepository: UserRepository
) {
    private val encoder = BCryptPasswordEncoder()

    @Value("\${admin.username}")
    private lateinit var adminUsername: String
    
    @Value("\${admin.password}")
    private lateinit var adminPassword: String

    @PostConstruct
    fun init() {
        val existingUser = userRepository.findByUsername(adminUsername)
        if (existingUser == null) {
            val hashedPassword = encoder.encode(adminPassword)
            val newUser = User(username = adminUsername, password = hashedPassword)
            userRepository.save(newUser)
            println("Default admin user created")
        }
    }
}

