package com.example.yourssu.user.service

import com.example.yourssu.JwtProvider
import com.example.yourssu.article.ArticleService
import com.example.yourssu.comment.CommentRepository
import com.example.yourssu.user.domain.User
import com.example.yourssu.user.exception.UserNotFoundException
import com.example.yourssu.user.exception.WrongPasswordException
import com.example.yourssu.user.repository.UserRepository
import com.example.yourssu.user.response.LoginResponse
import com.example.yourssu.user.response.RegisterResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val commentRepository: CommentRepository,
    private val articleService: ArticleService,
    private val jwtProvider: JwtProvider
) {


    fun createUser(user: User): RegisterResponse {
        val requestEmail: String = user.email

        if (userRepository.findByEmail(requestEmail).isPresent)
            throw IllegalArgumentException("User is already created")


        user.password = passwordEncoder.encode(user.password)

        val savedUser = userRepository.save(user)
        return RegisterResponse(savedUser.email, savedUser.username, savedUser.role.name)
    }

    @Transactional
    fun deleteUser(email: String, userPassword: String) {
        userRepository.findByEmail(email).ifPresentOrElse({
            validateUser(email, userPassword)

            commentRepository.deleteAllByUser(it)
            articleService.deleteArticleByUser(it)
            userRepository.delete(it)
        }, { throw UserNotFoundException() })
    }

    fun getUser(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { throw UserNotFoundException() }

    }

    /**
     * If user of email is present, and user's password is same with Database's password, nothing happen.
     * @param email email of user
     * @param password password of user before encrypted
     *
     * @exception UserNotFoundException If user doesn't exist
     * @exception WrongPasswordException If password is wrong
     */
    fun validateUser(email: String, password: String) {

        val user = userRepository.findByEmail(email).orElseThrow {
            throw UserNotFoundException()
        }
        if (!passwordEncoder.matches(password, user.password))
            throw WrongPasswordException()
    }

    fun loginUser(email: String, password: String): LoginResponse {

        validateUser(email, password)
        val user = getUser(email)

        val accessToken = jwtProvider.createToken(email, user.role, JwtProvider.ExpirationTime.ACCESS)
        val refreshToken = jwtProvider.createToken(email, user.role, JwtProvider.ExpirationTime.REFRESH)

        user.refreshToken = refreshToken
        userRepository.save(user)
        return LoginResponse(user, accessToken)
    }
}