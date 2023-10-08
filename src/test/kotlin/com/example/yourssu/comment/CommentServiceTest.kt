package com.example.yourssu.comment

import com.example.yourssu.article.domain.Article
import com.example.yourssu.article.exception.ArticleNotFoundException
import com.example.yourssu.article.repository.ArticleRepository
import com.example.yourssu.comment.domain.Comment
import com.example.yourssu.comment.repository.CommentRepository
import com.example.yourssu.comment.service.CommentService
import com.example.yourssu.comment.exception.CommentNotFoundException
import com.example.yourssu.user.domain.User
import com.example.yourssu.user.exception.UserNotFoundException
import com.example.yourssu.user.repository.UserRepository
import com.example.yourssu.user.exception.WrongPasswordException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*
import javax.transaction.Transactional

@Transactional
@SpringBootTest
class CommentServiceTest @Autowired constructor(
    val commentService: CommentService,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository,
    val passwordEncoder: PasswordEncoder,
    val commentRepository: CommentRepository
) {

    @Test
    fun createCommentSuccess() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        //when
        val createComment = commentService.createComment(comment, email, password, article.articleId)
        //then
        assertThat(createComment)
            .isEqualTo(comment)
    }

    @Test
    fun createCommentFailIfArticleNotFound() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val content = "content"
        val comment = Comment(content)

        //when
        val assertThrows = assertThrows<ArticleNotFoundException> {
            commentService.createComment(comment, email, password, 0)
        }
        val findById: Optional<Comment> = commentRepository.findById(comment.commentId)
        //then
        assertThat(assertThrows.message)
            .isEqualTo("Article Not Found")
        assertThat(findById)
            .isEmpty
    }

    @Test
    fun createCommentFailIfUserNotFound() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        val unknownUserEmail = "pmet@abc.com"
        //when
        val assertThrows = assertThrows<UserNotFoundException> {
            commentService.createComment(
                comment,
                unknownUserEmail,
                password,
                article.articleId
            )
        }
        //then
        assertThat(assertThrows.message)
            .isEqualTo("User Not Found")
    }

    @Test
    fun createCommentFailIfWrongPassword() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        val wrongPassword = "1234"
        //when
        val assertThrows = assertThrows<WrongPasswordException> {
            commentService.createComment(
                comment,
                email,
                wrongPassword,
                article.articleId
            )
        }
        //then
        assertThat(assertThrows.message)
            .isEqualTo("Password is wrong")
    }

    @Test
    fun modifyCommentSuccess() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        comment.user = user
        comment.article = article
        commentRepository.save(comment)

        comment.content = "content1"

        //when
        commentService.modifyComment(comment, email, password, article.articleId, comment.commentId)
        //then
        val findById = commentRepository.findById(comment.commentId)

        assertThat(findById)
            .isPresent
            .get()
            .hasFieldOrPropertyWithValue("content", "content1")
    }

    @Test
    fun modifyCommentFailIfCommentNotFound() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        comment.user = user
        comment.article = article

        //when
        val assertThrows = assertThrows<CommentNotFoundException> {
            commentService.modifyComment(comment, email, password, article.articleId, 1)
        }
        //then
        assertThat(assertThrows.message)
            .isEqualTo("Comment Not Found")
    }

    @Test
    fun modifyCommentFailIfArticleNotFound() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        comment.user = user
        comment.article = article
        commentRepository.save(comment)

        comment.content = "content1"

        //when
        val assertThrows = assertThrows<IllegalArgumentException> {
            commentService.modifyComment(
                comment,
                email,
                password,
                0,
                comment.commentId
            )
        }

        //then
        assertThat(assertThrows.message)
            .isEqualTo("Wrong Article Id")
    }

    @Test
    fun deleteCommentSuccess() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        comment.user = user
        comment.article = article
        commentRepository.save(comment)


        //when
        commentService.deleteComment(article.articleId, comment.commentId, email, password)
        //then
        val findById = commentRepository.findById(comment.commentId)

        assertThat(findById)
            .isEmpty
    }

    @Test
    fun deleteCommentFailIfArticleNotFound() {
        //given
        val email = "temp@abc.com"
        val password = "temp@!1"
        val username = "홍길동Ab"
        val encodedPasswd = passwordEncoder.encode(password)
        val user = User(email, encodedPasswd, username)
        userRepository.save(user)

        val title = "title"
        val content = "content"
        val article = Article(title, content)
        article.user = user
        articleRepository.save(article)

        val comment = Comment(content)
        comment.user = user
        comment.article = article
        commentRepository.save(comment)


        //when
        commentService.deleteComment(1, comment.commentId, email, password)
        //then
        val findById = commentRepository.findById(comment.commentId)

        assertThat(findById)
            .isPresent
    }
}