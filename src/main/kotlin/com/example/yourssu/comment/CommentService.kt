package com.example.yourssu.comment

import com.example.yourssu.JwtProvider
import com.example.yourssu.article.ArticleService
import com.example.yourssu.error.CommentNotFoundException
import com.example.yourssu.error.PermissionDeniedError
import com.example.yourssu.user.domain.User
import com.example.yourssu.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService,
    private val userService: UserService
) {

    fun createComment(comment: Comment, email: String, userPassword: String, articleId: Long): Comment {
        val user: User = userService.getUser(email)
        userService.validateUser(email, userPassword)
        val article = articleService.getArticleById(articleId)
        comment.article = article
        comment.user = user

        return commentRepository.save(comment)
    }

    fun modifyComment(
        comment: Comment,
        email: String,
        userPassword: String,
        articleId: Long,
        commentId: Long
    ): Comment {

        commentRepository.findById(commentId).ifPresentOrElse({
            val user: User = userService.getUser(email)
            userService.validateUser(email, userPassword)

            if (it.user != user) {
                throw PermissionDeniedError()
            }
            if (it.article.articleId != articleId) {
                throw IllegalArgumentException("Wrong Article Id")
            }
            it.content = comment.content
            commentRepository.flush()
        },{
            throw CommentNotFoundException()
        })
        return commentRepository.findById(commentId).orElseThrow()
    }


    fun deleteComment(articleId: Long, commentId: Long, email: String, userPassword: String) {
        commentRepository.findById(commentId).ifPresent {
            val user = userService.getUser(email)
            if(user != it.user) throw PermissionDeniedError()

            userService.validateUser(email, userPassword)

            if (articleId == it.article.articleId)
                commentRepository.deleteById(commentId)
        }
    }

}