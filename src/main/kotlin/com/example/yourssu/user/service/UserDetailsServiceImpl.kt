package com.example.yourssu.user.service

import com.example.yourssu.user.repository.UserRepository
import com.example.yourssu.user.domain.UserDetailsImpl
import com.example.yourssu.user.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {

        val usernameOrEmpty = username.orEmpty()

        val user = userRepository.findByEmail(usernameOrEmpty).orElseThrow {
            throw UserNotFoundException()
        }

        return UserDetailsImpl(user)
    }

}