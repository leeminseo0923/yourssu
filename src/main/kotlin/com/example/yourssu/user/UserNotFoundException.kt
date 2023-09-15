package com.example.yourssu.user

import com.example.yourssu.error.NotFoundException

class UserNotFoundException(override val message: String = "User Not Found") : NotFoundException(message)