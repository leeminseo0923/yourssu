package com.example.yourssu.comment.repository

import com.example.yourssu.article.domain.Article
import com.example.yourssu.comment.domain.Comment
import com.example.yourssu.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun deleteAllByArticle(article: Article)

    fun deleteAllByUser(user: User)
}
