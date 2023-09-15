package com.example.yourssu.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(private val userService: UserService) {


    /**
     * curl test
     * curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password", "username": "username"}'
     */
    @PostMapping("/user")
    fun create(@RequestBody user: User): UserOTD {
        return UserOTD(userService.createUser(user))
    }


    /**
     * curl test
     * curl -X DELETE http://localhost:8080/user -H "Content-Type: application/json" -d '{"email": "email1@urssu.com", "password": "password1"}'
     */
    @DeleteMapping("/user")
    fun delete(@RequestBody userDTO: UserDTO) {
        userService.deleteUser(userDTO.email, userDTO.password)
    }
}