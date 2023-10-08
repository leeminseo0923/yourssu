package com.example.yourssu.error

class PermissionDeniedError(override val message: String? = "Permission denied") : RuntimeException()