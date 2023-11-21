package com.example.yourssu.article.dto

import com.example.yourssu.article.domain.Article

data class ArticleDTO(
    val title: String,
    val content: String,
) {
    fun createEntity(): Article {
        if (title == "" || title == " " || content == "" || content == " ") {
            throw IllegalArgumentException("title or content shouldn't be blank: title-$title, content-$content")
        }
        return Article(title, content)
    }
}
