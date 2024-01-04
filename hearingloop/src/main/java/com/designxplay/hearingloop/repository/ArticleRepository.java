package com.designxplay.hearingloop.repository;

import com.designxplay.hearingloop.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    List<Article> findAll();
}
