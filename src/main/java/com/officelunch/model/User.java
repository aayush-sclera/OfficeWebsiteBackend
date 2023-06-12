package com.officelunch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    @Transient
    private String confirmPass;
    @ManyToMany(fetch = FetchType.EAGER )
    private List<Role> roles;
    private boolean stat = Boolean.FALSE;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
