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
@Table(name = "users")
public class User extends BaseEntity {
    // 유저 아이디
    @Column(nullable = false)
    private String userId;

    // 유저 비밀번호
    @Column(nullable = false)
    private String password;

    // 유저 이름
    @Column(nullable = false)
    private String name;

    // 유저 시장도감
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private MarketBook marketBook;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions;
}
