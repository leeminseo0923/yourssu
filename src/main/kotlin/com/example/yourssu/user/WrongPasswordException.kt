package com.example.yourssu.user

class WrongPasswordException(override val message: String? = "Password is wrong") : RuntimeException()