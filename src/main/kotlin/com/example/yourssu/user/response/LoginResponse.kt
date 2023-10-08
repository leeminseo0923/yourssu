package com.example.yourssu.user.response

import com.example.yourssu.user.domain.User

data class LoginResponse(
    var email: String,
    var username: String,
    var role: String,
    var accessToken: String,
    var refreshToken: String
) {
    constructor(user: User, accessToken: String) : this(user.email, user.username, user.role.name, accessToken, user.refreshToken)
}