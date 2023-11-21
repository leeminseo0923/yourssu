package com.example.yourssu.comment.response

import com.example.yourssu.comment.domain.Comment

data class CommentResponse(
    val commentId: Long,
    val email: String,
    val content: String,
) {
    constructor(comment: Comment) : this(comment.commentId, comment.user.email, comment.content)
}
