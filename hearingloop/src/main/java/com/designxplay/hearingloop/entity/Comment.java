package com.designxplay.hearingloop.entity;

import com.designxplay.hearingloop.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name ="article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;
    // ID 없이 Comment 객체 생성 (새 댓글용)


    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다");
        return new Comment(
//                dto.getId(),
                null,
                article,
                dto.getNickname(),
                dto.getBody()
        );
        // 엔티티 생성 및 반환
    }

    public void patch(CommentDto dto) {
        // 예외 발생(Url 에 있는 id와 dto 에 있는 id가 다를 때. 즉 Json 데이터의 id가 url의 id와 다를때. 여기서 this는 기존 댓글이다
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        // 객체 갱신
        if(dto.getNickname() != null) //수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname(); // 내용 반영
        if(dto.getBody() != null)// 수정할 본문 데이터가 있다면
            this.body = dto.getBody();
    }
}
