package com.nectcracker.studyproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class NewsUsers {

    @EmbeddedId
    private NewsUsersId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("newsId")
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usersId")
    private User users;

    @Column(name = "saw")
    private boolean saw;

    private NewsUsers(){}

    public NewsUsers(News news, User users) {
        this.news = news;
        this.users = users;
        this.id = new NewsUsersId(news.getId(),  users.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        NewsUsers that = (NewsUsers) o;
        return Objects.equals(news, that.news) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(news, users);
    }
}
