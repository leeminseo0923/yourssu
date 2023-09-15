package com.example.yourssu.article

import com.example.yourssu.comment.CommentRepository
import com.example.yourssu.user.User
import com.example.yourssu.user.UserNotFoundException
import com.example.yourssu.user.UserRepository
import com.example.yourssu.user.WrongPasswordException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ArticleService @Autowired constructor(
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val commentRepository: CommentRepository
) {

    fun createArticle(article: Article, userEmail: String, userPassword: String): Article {
        userRepository.findByEmail(userEmail).ifPresentOrElse({
            if (!passwordEncoder.matches(userPassword, it.password)) throw WrongPasswordException()

            article.user = it
        }, {
            throw UserNotFoundException()
        })


        return articleRepository.save(article)
    }

    fun modifyArticle(article: Article, userEmail: String, userPassword: String, articleId: Long): Article {
        articleRepository.findById(articleId).ifPresentOrElse({ oldArticle ->
            userRepository.findByEmail(userEmail).ifPresentOrElse({
                if (it != oldArticle.user) {
                    throw PermissionDeniedError()
                }
                passwordEncoder.matches(userPassword, it.password)
            }, {
                throw UserNotFoundException()
            })

            oldArticle.content = oldArticle.content
            oldArticle.title = oldArticle.title
            articleRepository.flush()
        }, {
            throw ArticleNotFoundException()
        })

        return articleRepository.findById(articleId).orElseThrow()
    }

    @Transactional
    fun deleteArticle(userEmail: String, userPassword: String, articleId: Long) {
        articleRepository.findById(articleId).ifPresent {
            userRepository.findByEmail(userEmail).ifPresentOrElse({ user ->
                if (it.user != user) throw PermissionDeniedError()
                passwordEncoder.matches(userPassword, user.password)

            }, { throw UserNotFoundException() })

            commentRepository.deleteAllByArticle(it)
            articleRepository.delete(it)
        }
    }

    @Transactional
    fun deleteArticleByUser(user: User) {
        val articles = articleRepository.findAllByUser(user)
        articles.forEach {
            commentRepository.deleteAllByArticle(it)
            articleRepository.delete(it)
        }
    }


    fun getArticleById(id: Long): Article {
        return articleRepository.findById(id).orElseThrow { throw ArticleNotFoundException() }
    }
}