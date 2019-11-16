package com.nectcracker.studyproject.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NewsUsersId implements Serializable {

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "news_id")
    private Long newsId;

    private NewsUsersId(){
    }

    public NewsUsersId(Long usersId, Long newsId) {
        this.usersId = usersId;
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        NewsUsersId that = (NewsUsersId) o;
        return Objects.equals(usersId, that.usersId) &&
                Objects.equals(newsId, that.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, newsId);
    }
}
