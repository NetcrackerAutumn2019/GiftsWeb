package com.nectcracker.studyproject.domain;


import lombok.Builder;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table
@Data
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Builder(toBuilder = true)
    public UserInfo(String firstName, String lastName, Date birthday, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.user = user;
    }

    public UserInfo(){
    }
}
