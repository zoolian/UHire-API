package com.uhire.rest.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserRole")
public class UserRole {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutorial_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;

}
