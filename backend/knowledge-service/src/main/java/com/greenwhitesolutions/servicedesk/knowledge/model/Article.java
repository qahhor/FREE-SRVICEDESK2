package com.greenwhitesolutions.servicedesk.knowledge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "kb_articles")
public class Article {

    @Id
    private String id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private boolean isPublic;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
