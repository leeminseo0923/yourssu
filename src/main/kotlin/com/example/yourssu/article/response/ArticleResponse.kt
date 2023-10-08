package com.example.yourssu.article.response

import com.example.yourssu.article.domain.Article

data class ArticleResponse(
    val articleId: Long,
    val email: String,
    val title: String,
    val content: String
) {
    constructor(article: Article) : this(article.articleId, article.user.email, article.title, article.content)
}
