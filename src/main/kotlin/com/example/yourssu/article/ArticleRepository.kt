package com.example.yourssu.article

import com.example.yourssu.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByUser(user: User): MutableList<Article>
}