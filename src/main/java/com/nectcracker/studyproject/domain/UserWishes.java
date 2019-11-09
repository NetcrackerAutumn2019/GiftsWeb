package com.nectcracker.studyproject.domain;

import lombok.Data;

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

    private String wishName;

    public UserWishes(User user, String wish) {
        this.user = user;
        this.wishName = wish;
    }

    public UserWishes() {
    }
}
