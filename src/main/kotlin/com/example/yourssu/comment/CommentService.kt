package com.example.yourssu.comment

import com.example.yourssu.article.ArticleService
import com.example.yourssu.article.PermissionDeniedError
import com.example.yourssu.user.User
import com.example.yourssu.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService,
    private val userService: UserService
) {

    fun createComment(comment: Comment, userEmail: String, userPassword: String, articleId: Long): Comment {
        val user: User = userService.getUser(userEmail)
        userService.validateUser(user, userPassword)

        val article = articleService.getArticleById(articleId)
        comment.article = article
        comment.user = user

        return commentRepository.save(comment)
    }

    fun modifyComment(
        comment: Comment,
        userEmail: String,
        userPassword: String,
        articleId: Long,
        commentId: Long
    ): Comment {

        commentRepository.findById(commentId).ifPresentOrElse({
            val user: User = userService.getUser(userEmail)
            userService.validateUser(user, userPassword)

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


    fun deleteComment(articleId: Long, commentId: Long, userEmail: String, userPassword: String) {
        commentRepository.findById(commentId).ifPresent {
            val user = userService.getUser(userEmail)
            if(user != it.user) throw PermissionDeniedError()

            userService.validateUser(user, userPassword)

            if (articleId == it.article.articleId)
                commentRepository.deleteById(commentId)
        }
    }

}