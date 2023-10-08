package com.example.yourssu.comment

import com.example.yourssu.user.dto.RegisterDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CommentController (val commentService: CommentService) {

    /**
     * curl -X POST http://localhost:8080/comment/1 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password", "content": "content"}'
     */
    @PostMapping("/comment/{articleId}")
    fun create(@PathVariable articleId: Long, @RequestBody commentDTO: CommentDTO): CommentOTD {
        return CommentOTD(
            commentService.createComment(
                commentDTO.createEntity(),
                commentDTO.email,
                commentDTO.password,
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
        @RequestBody commentDTO: CommentDTO
    ): CommentOTD {
        return CommentOTD(
            commentService.modifyComment(
                commentDTO.createEntity(),
                commentDTO.email,
                commentDTO.password,
                articleId,
                commentId
            )
        )
    }

    /**
     * curl -X DELETE http://localhost:8080/comment/1/3 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password"}'
     */
    @DeleteMapping("/comment/{articleId}/{commentId}")
    fun delete(@PathVariable articleId: Long, @PathVariable commentId: Long, @RequestBody registerDTO: RegisterDTO) {
        commentService.deleteComment(articleId, commentId, registerDTO.email, registerDTO.password)
    }
}