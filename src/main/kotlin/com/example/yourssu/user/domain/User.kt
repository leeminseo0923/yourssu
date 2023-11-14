package com.example.yourssu.user.domain

import com.example.yourssu.user.UserRole
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column
    var email: String = ""

    @Column
    var password: String = ""

    @Column
    var username: String = ""

    @Column
    @Enumerated(value = EnumType.STRING)
    var role: UserRole = UserRole.USER

    @Column
    var refreshToken: String = ""

    constructor(email: String, password: String, username: String, userRole: String) : this() {
        this.email = email
        this.password = password
        this.username = username
        if (userRole == "USER") this.role = UserRole.USER
        if (userRole == "ADMIN") this.role = UserRole.ADMIN
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (email != other.email) return false
        if (password != other.password) return false
        return username == other.username
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + username.hashCode()
        return result
    }


}