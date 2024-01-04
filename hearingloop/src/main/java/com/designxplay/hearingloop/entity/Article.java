package com.designxplay.hearingloop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Data // Lombok의 @Data 어노테이션을 사용
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String broadcastKiosk;
    @Column

    private String adName;

    @Column
    private String adType;
    @Column
    private Integer exposureTime;
    @Column
    private Integer dailyExposureCount;
    @Column

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Column

    private String notes;
    @Column
    private Boolean isActive;
    @ElementCollection
    private List<String> fileNames; // 파일 이름을 저장할 리스트 필드 추가


    public void patch(Article article) {
        if(article.broadcastKiosk != null)
        {
            this.broadcastKiosk = article.broadcastKiosk;
        }
        if(article.adName != null)
        {
            this.adName = article.adName;
        }if(article.adType != null)
        {
            this.adType = article.adType;
        }if(article.exposureTime != null)
        {
            this.exposureTime = article.exposureTime;
        }if(article.dailyExposureCount != null)
        {
            this.dailyExposureCount = article.dailyExposureCount;
        }if(article.startDate != null)
        {
            this.startDate = article.startDate;
        }if(article.endDate != null)
        {
            this.endDate = article.endDate;
        }if(article.notes != null)
        {
            this.notes = article.notes;
        }if(article.isActive != null)
        {
            this.isActive = article.isActive;
        }

        if(article.fileNames != null)
        {
            this.fileNames = article.fileNames;
        }
    }
}
