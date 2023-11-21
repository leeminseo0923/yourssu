package com.example.yourssu.user.repository

import com.example.yourssu.user.UserRole
import com.example.yourssu.user.domain.QUser
import com.example.yourssu.user.response.UserResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {

    override fun searchAll(
        username: String,
        email: String,
        createdAtStart: LocalDateTime,
        createdAtEnd: LocalDateTime,
        updatedAtStart: LocalDateTime,
        updatedAtEnd: LocalDateTime
    ): List<UserResponse> {
        val user = QUser.user
        val query = queryFactory.query()
            .select(
                Projections.fields(
                    UserResponse::class.java,
                    user.userId.`as`("id"),
                    user.email,
                    user.username,
                    user.role,
                    user.createdAt,
                    user.updatedAt
                )
            )
            .where(user.role.eq(UserRole.USER))
            .where(user.createdAt.gt(createdAtStart))
            .where(user.createdAt.lt(createdAtEnd))
            .where(user.updatedAt.gt(createdAtStart))
            .where(user.updatedAt.lt(createdAtEnd))

        if (username.isNotBlank()) {
            query.where(user.username.eq(username))
        }

        if (email.isNotBlank()) {
            query.where(user.email.eq(email))
        }

        return query
            .orderBy(user.userId.desc())
            .from(user)
            .fetch()
    }
}