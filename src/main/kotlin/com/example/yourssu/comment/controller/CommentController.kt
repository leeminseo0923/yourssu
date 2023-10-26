package com.example.yourssu.comment.controller

import com.example.yourssu.comment.dto.CommentDTO
import com.example.yourssu.comment.response.CommentResponse
import com.example.yourssu.comment.service.CommentService
import com.example.yourssu.security.Auth
import com.example.yourssu.security.AuthInfo
import com.example.yourssu.user.dto.RegisterDTO
import org.springframework.web.bind.annotation.*

@RestController
class CommentController (val commentService: CommentService) {

    /**
     * curl -X POST http://localhost:8080/comment/1 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password", "content": "content"}'
     */
    @PostMapping("/comment/{articleId}")
    fun create(@PathVariable articleId: Long, @RequestBody commentDTO: CommentDTO, @Auth authInfo: AuthInfo): CommentResponse {
        return CommentResponse(
            commentService.createComment(
                commentDTO.createEntity(),
                authInfo.email,
                articleId
            )
        )
    }

    /**
     * curl -X PUT http://localhost:8080/comment/1/3 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password", "content": "content2"}'
     */
    @PutMapping("/comment/{articleId}/{commentId}")
    fun modify(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @RequestBody commentDTO: CommentDTO,
        @Auth authInfo: AuthInfo
    ): CommentResponse {
        return CommentResponse(
            commentService.modifyComment(
                commentDTO.createEntity(),
                authInfo.email,
                articleId,
                commentId
            )
        )
    }

    /**
     * curl -X DELETE http://localhost:8080/comment/1/3 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password"}'
     */
    @DeleteMapping("/comment/{articleId}/{commentId}")
    fun delete(@PathVariable articleId: Long, @PathVariable commentId: Long, @RequestBody registerDTO: RegisterDTO, @Auth authInfo: AuthInfo) {
        commentService.deleteComment(articleId, commentId, authInfo.email)
    }
}