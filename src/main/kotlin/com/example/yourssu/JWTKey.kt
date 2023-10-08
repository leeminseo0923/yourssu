package com.example.yourssu

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix="jwt.secret")
data class JWTKey(val key: String)
