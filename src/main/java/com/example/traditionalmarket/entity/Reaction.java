package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "reactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "board_id"})})
public class Reaction extends BaseEntity {
    // 좋아요 or 싫어요
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
