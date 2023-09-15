package com.example.yourssu.user

import com.example.yourssu.article.ArticleRepository
import com.example.yourssu.article.ArticleService
import com.example.yourssu.comment.CommentRepository
import com.example.yourssu.comment.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService
) {


    fun createUser(user: User): User {
        val requestEmail: String = user.email

        if (userRepository.findByEmail(requestEmail).isPresent)
            throw IllegalArgumentException("User is already created")


        user.password = passwordEncoder.encode(user.password)

        return userRepository.save(user)
    }

    @Transactional
    fun deleteUser(userEmail: String, userPassword: String) {
        userRepository.findByEmail(userEmail).ifPresentOrElse({
            validateUser(it, userPassword)

            commentRepository.deleteAllByUser(it)
            articleService.deleteArticleByUser(it)
            userRepository.delete(it)
        },{throw UserNotFoundException()})
    }

    fun getUser(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { throw UserNotFoundException() }

    }

    fun validateUser(user: User, password: String) {

        if (!passwordEncoder.matches(password, user.password))
            throw WrongPasswordException()
    }
}