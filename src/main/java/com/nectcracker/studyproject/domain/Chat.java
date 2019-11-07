package com.nectcracker.studyproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wish_id")
    private UserWishes wishForChat;

    private String description;

    private Date deadline;

    private Double presentPrice;

    private Double currentPrice;

    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> participants = new HashSet<>();

    public Chat() {

    }

    public Chat(String description, Date deadline, Double presentPrice) {
        this.description = description;
        this.deadline = deadline;
        this.presentPrice = presentPrice;
        this.currentPrice = 0.0;
    }
}
