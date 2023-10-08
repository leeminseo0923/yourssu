package com.example.yourssu.comment.domain

import com.example.yourssu.article.domain.Article
import com.example.yourssu.user.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Comment() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var commentId: Long = 0

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    var content: String = ""

    constructor(content: String) : this() {
        this.content = content
    }

    @ManyToOne
    @JoinColumn(name = "article_id")
    lateinit var article: Article

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: User
}