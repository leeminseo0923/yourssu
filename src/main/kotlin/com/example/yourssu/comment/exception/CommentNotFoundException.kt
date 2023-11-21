package com.example.yourssu.comment.exception

import com.example.yourssu.error.NotFoundException

class CommentNotFoundException(message: String = "Comment Not Found") : NotFoundException(message)
