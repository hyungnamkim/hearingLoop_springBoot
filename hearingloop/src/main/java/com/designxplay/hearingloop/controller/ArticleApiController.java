package com.designxplay.hearingloop.controller;

import com.designxplay.hearingloop.dto.ArticleForm;
import com.designxplay.hearingloop.entity.Article;
import com.designxplay.hearingloop.repository.ArticleRepository;
import com.designxplay.hearingloop.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/api/articles")
    public List<Article> index()
    {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id)
    {
        return articleService.show(id);
    }
    @PostMapping("api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto)
    {
        Article created = articleService.create(dto);
        return(created != null)?
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


    }
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto)
    {
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
            ResponseEntity.status(HttpStatus.OK).body(updated):
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id)
    {
        Article delete = articleService.delete(id);
        return (delete != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build():
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createdArticles(dtos);
        return (createdList !=null)?
                ResponseEntity.status(HttpStatus.OK).body(createdList):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

}
