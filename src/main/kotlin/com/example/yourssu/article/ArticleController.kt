package com.example.yourssu.article

import com.example.yourssu.user.dto.LoginDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController @Autowired constructor(private val articleService: ArticleService) {

    /**
     * curl test
     * curl -X POST http://localhost:8080/article -H "Content-Type: application/json" -H "Authorization: " -d '{"email": "email@urssu.com", "password": "password1", "title": "title", "content": "content"}'
     */
    @PostMapping("/article")
    fun create(@RequestBody articleDTO: ArticleDTO): ArticleOTD {
        return ArticleOTD(
            articleService.createArticle(
                articleDTO.createEntity(),
                articleDTO.email,
                articleDTO.password,
            )
        )
    }


    /**
     * curl test
     * curl -X PUT http://localhost:8080/article/1 -H "Content-Type: application/json" -H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbDFAdXJzc3UuY29tIiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjk2NzcyOTc2LCJleHAiOjE2OTY3NzQ3NzZ9.vzJ1qASStlnZC1_jBTx22Whu86iUoDDbZ1P-1fSZDtw" -d '{"email": "email@urssu.com", "password": "password", "title": "title", "content": "content1"}'
     */
    @PutMapping("/article/{id}")
    fun modify(@PathVariable id: Long, @RequestBody articleDTO: ArticleDTO): ArticleOTD {
        return ArticleOTD(articleService.modifyArticle(articleDTO.createEntity(), articleDTO.email, articleDTO.password, id))
    }

    /**
     * curl -X DELETE http://localhost:8080/article/1 -H "Content-Type: application/json" -d '{"email": "email@urssu.com", "password": "password"}'
     */
    @DeleteMapping("/article/{id}")
    fun delete(@PathVariable id: Long, @RequestBody loginDTO: LoginDTO) {
        articleService.deleteArticle(loginDTO.email, loginDTO.password, id)
    }

}