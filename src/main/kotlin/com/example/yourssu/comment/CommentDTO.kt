package com.example.yourssu.comment

data class CommentDTO(
    val email: String,
    val password: String,
    val content: String
) {
    fun createEntity(): Comment {
        if (content == "" || content == " ") {
            throw IllegalArgumentException("content shouldn't be blank: $content")
        }
        return Comment(content)
    }
}
