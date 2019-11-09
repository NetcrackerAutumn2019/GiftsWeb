package com.nectcracker.studyproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @OneToOne(mappedBy = "wishForChat", cascade = CascadeType.ALL)
    private Chat chatForWish;

    public UserWishes(User user, String wish) {
        this.user = user;
        this.wishName = wish;
    }

    public UserWishes() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWishes that = (UserWishes) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserWishes{" +
                "id=" + id +
                ", wishName='" + wishName + '\'' +
                '}';
    }
}
