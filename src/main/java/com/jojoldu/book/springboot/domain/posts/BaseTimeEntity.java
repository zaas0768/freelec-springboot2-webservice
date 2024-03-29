package com.jojoldu.book.springboot.domain.posts;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass           // 1
@EntityListeners(AuditingEntityListener.class)      // 2
public class BaseTimeEntity {

    // Entity가 생성되어 저장될 때 시간이 자동으로 저장
    @CreatedDate
    private LocalDateTime createdDate;

    // 조회한 Entity 값을 변경할 때 시간이 자동으로 저장
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}

// 1.@MappedSuperclass
//  JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식함

// 2.@EntityListeners(AuditingEntityListener.class)
//  BaseTimeEntity 클래스에 Auditing 기능을 포함 시킴