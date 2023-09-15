package com.example.yourssu.comment

import com.example.yourssu.article.Article
import com.example.yourssu.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {
    fun deleteAllByArticle(article: Article)
    fun deleteAllByUser(user: User)

}