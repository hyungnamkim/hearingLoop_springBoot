package com.designxplay.hearingloop.service;

import com.designxplay.hearingloop.dto.ArticleForm;
import com.designxplay.hearingloop.entity.Article;
import com.designxplay.hearingloop.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);

    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
        {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. DTO -> 엔티티 반환하기
        Article article = dto.toEntity();
        log.info("id:{}, article: {}", id, article.toString());
        //2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target ==null || id != article.getId()){
            log.info("잘못된 요청! id:{}, article:{}", id, article.toString());
            return null;
        }

        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target ==null)
        {
            return null;
        }
        //3.대상 삭제하기
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createdArticles(List<ArticleForm> dtos) {
        //1.dto  묶음을 엔티티 묶음으로 변환하기
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //2.엔티티 묶음을 db에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //3.강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));
        //4.결과 값 반환하기
        return articleList;
    }
}