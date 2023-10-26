package com.example.yourssu.article.service

import com.example.yourssu.article.domain.Article
import com.example.yourssu.comment.repository.CommentRepository
import com.example.yourssu.article.exception.ArticleNotFoundException
import com.example.yourssu.article.repository.ArticleRepository
import com.example.yourssu.error.PermissionDeniedError
import com.example.yourssu.user.domain.User
import com.example.yourssu.user.exception.UserNotFoundException
import com.example.yourssu.user.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ArticleService (
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) {

    fun createArticle(article: Article, userEmail: String): Article {
        userRepository.findByEmail(userEmail).ifPresentOrElse({
            article.user = it
        }, {
            throw UserNotFoundException()
        })

        return articleRepository.save(article)
    }

    fun modifyArticle(article: Article, userEmail: String, articleId: Long): Article {
        articleRepository.findById(articleId).ifPresentOrElse({ oldArticle ->
            userRepository.findByEmail(userEmail).ifPresentOrElse({

                if (it != oldArticle.user) {
                    throw PermissionDeniedError()
                }

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
    fun deleteArticle(userEmail: String, articleId: Long) {
        articleRepository.findById(articleId).ifPresent {
            userRepository.findByEmail(userEmail).ifPresentOrElse({ user ->
                if (it.user != user) throw PermissionDeniedError()

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