package com.example.yourssu.user.repository

import com.example.yourssu.user.response.UserResponse
import java.time.LocalDateTime

interface UserRepositoryCustom {
    fun searchAll(
        username: String,
        email: String,
        createdAtStart: LocalDateTime,
        createdAtEnd: LocalDateTime,
        updatedAtStart: LocalDateTime,
        updatedAtEnd: LocalDateTime,
    ): List<UserResponse>
}
