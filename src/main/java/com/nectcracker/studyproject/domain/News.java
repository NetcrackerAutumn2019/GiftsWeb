package com.nectcracker.studyproject.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToMany
    @JoinTable(name = "user_has_news",
            joinColumns = @JoinColumn(name = "new_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> usersNews;


}
