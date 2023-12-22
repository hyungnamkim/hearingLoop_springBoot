package com.designxplay.hearingloop.dto;

import com.designxplay.hearingloop.entity.Article;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data // Lombok의 @Data 어노테이션을 사용
public class ArticleForm {
    private Long id;
    private String broadcastKiosk;
    private String adName;
    private String adType;
    private Integer exposureTime;
    private Integer dailyExposureCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String notes;
    private Boolean isActive;

    public Article toEntity() {
        // 새 게시물의 경우 id를 null로 설정하여 데이터베이스가 자동으로 id를 생성하도록 함
        return new Article(null, broadcastKiosk, adName, adType, exposureTime, dailyExposureCount, startDate, endDate, notes, isActive);
    }


    // Lombok이 자동으로 세터, 게터, toString 등을 생성합니다.
}
