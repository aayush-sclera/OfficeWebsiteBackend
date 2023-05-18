package com.officelunch.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String foodPref;
    private String attendance = "Absent";
    @OneToOne
    private User user;
    private String username;

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", foodPref='" + foodPref + '\'' +
                ", attendance='" + attendance + '\'' +
                ", user=" + user +
                ", username='" + username + '\'' +
                '}';
    }
}
