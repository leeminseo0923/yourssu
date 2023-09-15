package com.example.yourssu.comment

data class CommentOTD (
    val commentId: Long,
    val email: String,
    val content: String
) {
    constructor(comment: Comment) : this(comment.commentId, comment.user.email, comment.content)
}