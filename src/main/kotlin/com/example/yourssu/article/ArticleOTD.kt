package com.example.yourssu.article

data class ArticleOTD(
    val articleId: Long,
    val email: String,
    val title: String,
    val content: String
) {
    constructor(article: Article) : this(article.articleId, article.user.email, article.title, article.content)
}
