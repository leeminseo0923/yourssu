package com.example.yourssu.user

data class UserOTD(
    var email: String,
    var username: String
) {
    constructor(user: User) : this(user.email, user.username)
}