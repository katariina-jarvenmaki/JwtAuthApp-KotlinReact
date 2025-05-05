package com.katariinatuulia.backend

import com.katariinatuulia.backend.auth.JwtUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Test
    fun testToken() {
        val token = jwtUtils.generate("testuser")
        println("Token: $token")
        assert(jwtUtils.validate(token))
        assert(jwtUtils.username(token) == "testuser")
    }
}
