package com.example.yourssu.user.controller

import com.example.yourssu.security.Auth
import com.example.yourssu.security.AuthInfo
import com.example.yourssu.user.domain.User
import com.example.yourssu.user.dto.LoginDTO
import com.example.yourssu.user.dto.RegisterDTO
import com.example.yourssu.user.service.UserService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class UserController @Autowired constructor(private val userService: UserService) {


    /**
     * curl test
     * curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password", "username": "username", "role": "USER"}'
     */
    @PostMapping("/user")
    fun create(@RequestBody userDTO: RegisterDTO): ResponseEntity<Any?> {
        val user = User(userDTO.email, userDTO.password, userDTO.username, userDTO.role)
        return ResponseEntity(userService.createUser(user), HttpStatus.CREATED)
    }

    /**
     * curl test
     * curl -X DELETE http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password1"}'
     */
    @DeleteMapping("/user")
    @SecurityRequirement(name = "JWT auth")
    fun delete(@RequestBody user: LoginDTO, @Parameter(hidden = true) @Auth authInfo: AuthInfo) {
        userService.deleteUser(authInfo.email, user.password)
    }

    /**
     * curl -X POST http://localhost:8080/user/login -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password"}'
     */
    @PostMapping("/user/login")
    fun login(@RequestBody user: LoginDTO): ResponseEntity<Any?> {
        return ResponseEntity(userService.loginUser(user.email, user.password), HttpStatus.OK)
    }

    @GetMapping("/user/show")
    @SecurityRequirement(name = "JWT auth")
    fun all(
        @RequestParam(defaultValue = "") email: String,
        @RequestParam(defaultValue = "") username: String,
        @RequestParam(defaultValue = "1000-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") createdAtStart: LocalDate,
        @RequestParam(defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") createdAtEnd: LocalDate,
        @RequestParam(defaultValue = "1000-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") updatedAtStart: LocalDate,
        @RequestParam(defaultValue = "9999-12-31") @DateTimeFormat(pattern = "yyyy-MM-dd") updatedAtEnd: LocalDate,
        @Parameter(hidden = true) @Auth authInfo: AuthInfo
    ): ResponseEntity<List<Any?>> {

        val users = userService.getAllUser(
            username,
            email,
            createdAtStart.atStartOfDay(),
            createdAtEnd.atStartOfDay(),
            updatedAtStart.atStartOfDay(),
            updatedAtEnd.atStartOfDay()
        )

        return ResponseEntity(users, HttpStatus.OK)
    }
}