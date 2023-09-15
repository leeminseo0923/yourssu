package com.example.yourssu.article

import com.example.yourssu.error.NotFoundException

class ArticleNotFoundException(override val message: String = "Article Not Found") : NotFoundException(message)