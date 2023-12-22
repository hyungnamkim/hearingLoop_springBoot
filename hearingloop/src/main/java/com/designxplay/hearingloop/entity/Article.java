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


}
