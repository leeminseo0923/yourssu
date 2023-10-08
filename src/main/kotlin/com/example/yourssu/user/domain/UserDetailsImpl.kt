package com.example.yourssu.user.domain

import com.example.yourssu.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserDetailsImpl(private val user: User): UserDetails {

    fun getUser(): User {
        return user;
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return listOf(user.role.name).stream().map { SimpleGrantedAuthority(it) }.collect(Collectors.toList())
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}