package com.katariinatuulia.com.backend.jwt_auth.repository

//******************** IMPORTS ********************//

import com.katariinatuulia.com.backend.jwt_auth.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
