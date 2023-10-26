package com.example.yourssu.comment.dto

import com.example.yourssu.comment.domain.Comment

data class CommentDTO(
    val email: String,
    val content: String
) {
    fun createEntity(): Comment {
        if (content == "" || content == " ") {
            throw IllegalArgumentException("content shouldn't be blank: $content")
        }
        return Comment(content)
    }
}
