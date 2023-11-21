package com.example.yourssu.user.repository

import com.example.yourssu.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
    fun findByEmail(email: String): Optional<User>
}
