package com.designxplay.hearingloop.repository;

import com.designxplay.hearingloop.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /*Case 1: 4번 게시글의 모든 댓글 조회*/
        {

        }
    }

    @Test
    void findByNickname() {
    }
}