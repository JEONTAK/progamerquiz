package com.pq.progamerquiz.domain.mainPage;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Quiz {
    // Getter와 Setter 메소드들
    private String url;
    private String imageUrl;
    private String altText;
    private String title;
    private String description;

    // 기본 생성자
    public Quiz() {}

    // 모든 필드를 사용하는 생성자
    public Quiz(String url, String imageUrl, String title, String description) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.altText = title;
        this.title = title;
        this.description = description;
    }

}
