package com.example.yourssu.user.controller

import com.example.yourssu.user.domain.User
import com.example.yourssu.user.dto.LoginDTO
import com.example.yourssu.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(private val userService: UserService) {


    /**
     * curl test
     * curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password", "username": "username", "role": "USER"}'
     */
    @PostMapping("/user")
    fun create(@RequestBody user: User): ResponseEntity<Any?> {
        return ResponseEntity(userService.createUser(user), HttpStatus.CREATED)
    }

    /**
     * curl test
     * curl -X DELETE http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password1"}'
     */
    @DeleteMapping("/user")
    fun delete(@RequestBody user: LoginDTO) {
        userService.deleteUser(user.email, user.password)
    }

    /**
     * curl -X POST http://localhost:8080/user/login -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password"}'
     */
    @PostMapping("/user/login")
    fun login(@RequestBody user: LoginDTO): ResponseEntity<Any?> {
        return ResponseEntity(userService.loginUser(user.email, user.password), HttpStatus.OK)
    }
}