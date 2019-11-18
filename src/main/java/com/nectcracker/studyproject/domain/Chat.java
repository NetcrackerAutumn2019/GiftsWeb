package com.nectcracker.studyproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    private UserWishes wishForChat;

    private String description;

    private Date deadline;

    private Double presentPrice;

    private Double currentPrice;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "wish_for_chat"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> participants = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY ,targetEntity = Messages.class)
    private Set<Messages> messages = new HashSet<>();

    public Chat() {

    }

    public Chat(String description, Date deadline, Double presentPrice) {
        this.description = description;
        this.deadline = deadline;
        this.presentPrice = presentPrice;
        this.currentPrice = 0.0;
    }

    public void updateCurrentPrice(Double sum) {
        currentPrice += sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) &&
                Objects.equals(wishForChat, chat.wishForChat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wishForChat);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", wishForChat=" + wishForChat +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", presentPrice=" + presentPrice +
                '}';
    }
}
