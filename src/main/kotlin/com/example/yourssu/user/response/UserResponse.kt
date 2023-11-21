package com.example.yourssu.user.response

import com.example.yourssu.user.UserRole
import java.time.LocalDateTime

data class UserResponse(var id: Long,
                   var email: String,
                   var username: String,
                   var role: UserRole,
                   var createdAt: LocalDateTime,
                   var updatedAt: LocalDateTime) {

    constructor() : this(0, "", "", UserRole.USER, LocalDateTime.now(), LocalDateTime.now())

}