package com.nectcracker.studyproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "Interests")
public class Interests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "interestsSet", fetch = FetchType.EAGER)
    private Set<User> userSet;

    private String interestName;

    public Interests(String interestName) {
        this.interestName = interestName;
    }

    public Interests() {

    }

    @Override
    public String toString() {
        return "Interests{" +
                "interestName='" + interestName + '\'' +
                '}';
    }
}
