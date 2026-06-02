package com.example.propertymanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    private String targetRole; // "ALL", "RESIDENT", "STAFF"
    
    private LocalDateTime createTime;
    
    private String publisherName;

    public Notice() {
        this.createTime = LocalDateTime.now();
    }

    public Notice(String title, String content, String targetRole, String publisherName) {
        this.title = title;
        this.content = content;
        this.targetRole = targetRole;
        this.publisherName = publisherName;
        this.createTime = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
}
