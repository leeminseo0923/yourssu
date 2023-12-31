package com.example.yourssu.article.domain

import com.example.yourssu.user.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
@EntityListeners(AuditingEntityListener::class)
class Article() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var articleId: Long = 0

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var content: String = ""

    @Column(nullable = false)
    var title: String = ""

    constructor(title: String, content: String) : this() {
        this.title = title
        this.content = content
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
}
