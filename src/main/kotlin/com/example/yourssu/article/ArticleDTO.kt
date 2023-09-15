package com.example.yourssu.article

data class ArticleDTO(
    val email: String,
    val password: String,
    val title: String,
    val content: String
) {
    fun createEntity(): Article {
        if (title == "" || title == " " || content == "" || content == " ") {
            throw IllegalArgumentException("title or content shouldn't be blank: title-$title, content-$content")
        }
        return Article(title, content)
    }
}