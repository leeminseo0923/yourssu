package com.example.yourssu.article

class PermissionDeniedError(override val message: String? = "Permission denied") : RuntimeException()