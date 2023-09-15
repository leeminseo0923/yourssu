package com.example.yourssu.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import java.lang.IllegalArgumentException
import java.util.*
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun successToCreateNormalUser() {
        //given
        val user = User(email = "temp@abc.com", password = "1234@!abc", username = "홍길동Ab")
        //when
        val createUser = userService.createUser(user)
        //then
        assertThat(createUser)
            .isEqualTo(user)
    }

    @Test
    fun failToCreateUserIfSameUserExist() {
        //given
        val user = User(email = "temp@abc.com", password = "1234@!abc", username = "홍길동Ab")
        userService.createUser(user)
        //when
        val assertThrows = assertThrows<IllegalArgumentException> {
            userService.createUser(user)
        }
        //then
        assertThat(assertThrows.message)
            .isEqualTo("User is already created")
    }

    @Test
    fun userPasswordShouldBeEncoded() {
        //given
        val password = "1234@!abc"
        val user = User(email = "temp@abc.com", password = password, username = "홍길동Ab")
        //when
        userService.createUser(user)
        assertThat(passwordEncoder.matches(password, user.password))
            .isTrue()
    }

    @Test
    fun getUserReturnUser() {
        //given
        val user = User(email = "temp@abc.com", password = "1234@!abc", username = "홍길동Ab")
        userService.createUser(user)
        //when
        val get = userService.getUser(user.email)
        //then
        assertThat(get)
            .isEqualTo(user)
    }

    @Test
    fun failToGetUserIfNotExist() {
        //given
        val email: String = "temp@abc.com"
        //when
        val userNotFoundException = assertThrows<UserNotFoundException> {
            userService.getUser(email)
        }
        //then
        assertThat(userNotFoundException.message)
            .isEqualTo("User Not Found")
    }

    @Test
    fun successValidatePassword() {
        //given
        val password = "1234@!abc"
        val user = User("temp@abc.com", password, "홍길동Ab")
        user.password = passwordEncoder.encode(password)
        //when
        userService.validateUser(user, password)
        //then
    }

    @Test
    fun failValidatePassword() {
        //given
        val password1 = "1234@!abc"
        val password2 = "1234!@abc"
        val user = User("temp@abc.com", password1, "홍길동Ab")
        userService.createUser(user)
        //when
        val assertThrows = assertThrows<WrongPasswordException> {
            userService.validateUser(user, password2)
        }
        //then
        assertThat(assertThrows.message)
            .isEqualTo("Password is wrong")
    }

    @Test
    fun deleteUserIfUserExistAndPasswordEqual() {
        //given
        val user = User("temp@abc.com", passwordEncoder.encode("abc@123"), "홍길동Ab")
        userRepository.save(user)
        //when
        userService.deleteUser(user.email, "abc@123")
        //then
        val findById: Optional<User> = userRepository.findById(user.userId)

        assertThat(findById).isEmpty
    }

    @Test
    fun deleteUserFailIfPasswordUnEqual() {
        //given
        val user = User("temp@abc.com", passwordEncoder.encode("abc@123"), "홍길동Ab")
        userRepository.save(user)
        //when
        val exception = assertThrows<WrongPasswordException> {
            userService.deleteUser(user.email, user.password)
        }
        val findById: Optional<User> = userRepository.findById(user.userId)
        //then
        assertThat(exception.message).isEqualTo("Password is wrong")
        assertThat(findById).isPresent.isEqualTo(Optional.of(user))
    }

    @Test
    fun deleteUserFailIfUserNotExist() {
        //given
        val user = User("temp@abc.com", passwordEncoder.encode("abc@123"), "홍길동Ab")
        userRepository.save(user)
        //when
        val exception = assertThrows<UserNotFoundException> {
            userService.deleteUser("not@abc.com", "1234")
        }
        val findById: Optional<User> = userRepository.findById(user.userId)
        //then
        assertThat(exception.message).isEqualTo("User Not Found")
        assertThat(findById).isPresent.isEqualTo(Optional.of(user))
    }
}