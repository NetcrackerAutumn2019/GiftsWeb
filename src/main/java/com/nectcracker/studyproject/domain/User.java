package com.nectcracker.studyproject.domain;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Users")
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String activationCode;
    private String userCalendarId;
    private boolean confirmed = false;
    @Column(unique = true)
    private Long vkId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserInfo info;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = UserWishes.class)
    private Set<UserWishes> wishes;

    @ManyToMany
    @JoinTable(name = "tbl_friends",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends;

    @ManyToMany
    @JoinTable(name = "tbl_friends",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> friendsOf;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_has_interest",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id", referencedColumnName = "id"))
    private Set<Interests> interestsSet = new HashSet<>();

    @ManyToMany(mappedBy = "participants")
    private Set<Chat> userChats;

    @OneToMany(fetch = FetchType.LAZY ,targetEntity = Chat.class)
    private Set<Chat> chatsOwner;

    @ManyToMany(mappedBy = "usersNews")
    private Set<News> userNews;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, Long vkId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.vkId = vkId;
    }

    public User(String username, String password, Long vkId) {
        this.username = username;
        this.password = password;
        this.vkId = vkId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String toString(){
        return "id: " + id +"; username: " + username;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
