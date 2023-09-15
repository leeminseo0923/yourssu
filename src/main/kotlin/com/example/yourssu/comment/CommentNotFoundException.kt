package com.example.yourssu.comment

import com.example.yourssu.error.NotFoundException

class CommentNotFoundException(message: String = "Comment Not Found") : NotFoundException(message)