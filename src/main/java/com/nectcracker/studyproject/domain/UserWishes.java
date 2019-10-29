package com.nectcracker.studyproject.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
public class UserWishes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private String wish;

    public UserWishes(User user, String wish) {
        this.user = user;
        this.wish = wish;
    }

    public UserWishes() {
    }
}
