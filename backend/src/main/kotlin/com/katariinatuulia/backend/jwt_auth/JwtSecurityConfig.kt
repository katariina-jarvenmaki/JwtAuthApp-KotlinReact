package com.katariinatuulia.backend.jwt_auth

//******************** IMPORTS ********************//

import com.katariinatuulia.backend.jwt_auth.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtFilter: JwtFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // CSRF on poistettu, koska käytämme JWT-pohjaista autentikointia, jossa ei käytetä evästeitä (cookieja). CSRF-hyökkäykset kohdistuvat evästepohjaisiin sessioihin, eikä tämä riski ole läsnä token-pohjaisessa järjestelmässä.
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                .requestMatchers("/", "/index.html", "/static/**", "/*.html",
                    "/api/public/**", "/api/healthcheck", "/api/auth/login"
                ).permitAll()
                .requestMatchers("/api/secure/data").authenticated() // require authentication
                .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}