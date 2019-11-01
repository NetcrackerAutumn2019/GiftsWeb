package com.nectcracker.studyproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "Interests")
public class Interests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "interestsSet")
    private Set<User> userSet;

    private String interestName;

    public Interests(String interestName) {
        this.interestName = interestName;
    }

    public Interests() {

    }
}
