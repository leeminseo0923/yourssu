package com.example.yourssu.user.exception

class WrongPasswordException(override val message: String? = "Password is wrong") : RuntimeException()