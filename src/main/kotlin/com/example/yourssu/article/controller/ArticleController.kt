package com.example.yourssu.article.controller

import com.example.yourssu.article.dto.ArticleDTO
import com.example.yourssu.article.response.ArticleResponse
import com.example.yourssu.article.service.ArticleService
import com.example.yourssu.security.Auth
import com.example.yourssu.security.AuthInfo
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@SecurityRequirement(name = "JWT auth")
class ArticleController @Autowired constructor(private val articleService: ArticleService) {

    /**
     * curl test
     * curl -X POST http://localhost:8080/article -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAdXJzc3UuY29tIiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjk4MzE4NDAwLCJleHAiOjE2OTgzMjIwMDB9.bpBS_GZDTdwGdPWKtfo4qvFMEX58dZf42TRzHQkNQPc" -d '{"email": "email@urssu.com", "password": "password1", "title": "title", "content": "content"}'
     */
    @PostMapping("/article")
    fun create(@RequestBody articleDTO: ArticleDTO, @Parameter(hidden = true)@Auth authInfo: AuthInfo): ArticleResponse {
        return ArticleResponse(
            articleService.createArticle(
                articleDTO.createEntity(),
                authInfo.email,
            )
        )
    }


    /**
     * curl test
     * curl -X PUT http://localhost:8080/article/1 -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAdXJzc3UuY29tIiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjk2NzcyOTc2LCJleHAiOjE2OTY3NzQ3NzZ9.vzJ1qASStlnZC1_jBTx22Whu86iUoDDbZ1P-1fSZDtw" -d '{"email": "email@urssu.com", "password": "password", "title": "title", "content": "content1"}'
     */
    @PutMapping("/article/{id}")
    fun modify(@Parameter(hidden = true)@Auth authInfo: AuthInfo, @PathVariable id: Long, @RequestBody articleDTO: ArticleDTO): ArticleResponse {
        return ArticleResponse(articleService.modifyArticle(articleDTO.createEntity(), authInfo.email, id))
    }

    /**
     * curl -X DELETE http://localhost:8080/article/1 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password"}'
     */
    @DeleteMapping("/article/{id}")
    fun delete(@PathVariable id: Long, @Parameter(hidden = true) @Auth authInfo: AuthInfo) {
        articleService.deleteArticle(authInfo.email, id)
    }

}