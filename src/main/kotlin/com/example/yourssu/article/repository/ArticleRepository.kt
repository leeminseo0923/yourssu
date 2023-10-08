package com.example.yourssu.article.repository

import com.example.yourssu.article.domain.Article
import com.example.yourssu.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByUser(user: User): MutableList<Article>
}