package com.nectcracker.studyproject.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Chat.class)
    @JoinColumn(name = "wish_for_chat")
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User author;

    private String text;

    public Messages() {

    }

    public Messages(Chat chat, User author, String text) {
        this.chat = chat;
        this.author = author;
        this.text = text;
    }

    @Override
    public String toString() {
        return "{" +
                author +
                ", text: " + text +
                '}';
    }
}
