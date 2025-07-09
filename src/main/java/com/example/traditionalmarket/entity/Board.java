package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "boards")
public class Board extends BaseEntity {
    // 게시글 제목
    private String title;

    // 게시글 내용
    private String content;

    // 게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 좋아요 수
    private int likeCount;

    // 싫어요 수
    private int disLikeCount;

    // 시장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions;

    public Board setTitle(String title) {
        this.title = title;
        return this;
    }

    public Board setContent(String content) {
        this.content = content;
        return this;
    }
}
