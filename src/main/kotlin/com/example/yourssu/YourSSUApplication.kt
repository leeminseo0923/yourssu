package com.example.yourssu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class YourSSUApplication

fun main(args: Array<String>) {
    runApplication<YourSSUApplication>(*args)
}
