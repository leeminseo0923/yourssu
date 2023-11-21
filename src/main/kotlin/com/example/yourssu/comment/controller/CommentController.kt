package com.example.yourssu.comment.controller

import com.example.yourssu.comment.dto.CommentDTO
import com.example.yourssu.comment.response.CommentResponse
import com.example.yourssu.comment.service.CommentService
import com.example.yourssu.security.Auth
import com.example.yourssu.security.AuthInfo
import com.example.yourssu.user.dto.RegisterDTO
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = "JWT auth")
class CommentController(val commentService: CommentService) {
    /**
     * curl -X POST http://localhost:8080/comment/1 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password", "content": "content"}'
     */
    @PostMapping("/comment/{articleId}")
    fun create(
        @PathVariable articleId: Long,
        @RequestBody commentDTO: CommentDTO,
        @Parameter(hidden = true) @Auth authInfo: AuthInfo,
    ): CommentResponse {
        return CommentResponse(
            commentService.createComment(
                commentDTO.createEntity(),
                authInfo.email,
                articleId,
            ),
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
        @Parameter(hidden = true)
        @Auth authInfo: AuthInfo,
    ): CommentResponse {
        return CommentResponse(
            commentService.modifyComment(
                commentDTO.createEntity(),
                authInfo.email,
                articleId,
                commentId,
            ),
        )
    }

    /**
     * curl -X DELETE http://localhost:8080/comment/1/3 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password"}'
     */
    @DeleteMapping("/comment/{articleId}/{commentId}")
    fun delete(
        @PathVariable articleId: Long,
        @PathVariable commentId: Long,
        @RequestBody registerDTO: RegisterDTO,
        @Parameter(hidden = true)@Auth authInfo: AuthInfo,
    ) {
        commentService.deleteComment(articleId, commentId, authInfo.email)
    }
}
