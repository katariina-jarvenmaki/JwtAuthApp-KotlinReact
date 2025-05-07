package com.katariinatuulia.backend

//******************** IMPORTS ********************//

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HomeController {

    @GetMapping("/home")
    fun home(): String {
        return "Loading login form..."
    }
}
