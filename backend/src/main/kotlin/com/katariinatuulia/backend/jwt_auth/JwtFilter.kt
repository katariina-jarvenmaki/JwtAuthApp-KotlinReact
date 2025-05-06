package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.JwtUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory

@Component
class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(JwtFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7)

        if (token != null && jwtUtils.validate(token)) {
            val username = jwtUtils.username(token)
            val auth = UsernamePasswordAuthenticationToken(username, null, emptyList())
            auth.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = auth
            logger.info("Authenticated user: $username")
        }

        filterChain.doFilter(request, response)
    }
}