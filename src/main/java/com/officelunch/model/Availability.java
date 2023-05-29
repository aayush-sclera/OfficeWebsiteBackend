package com.officelunch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String foodPref;
    @OneToOne
    private User user;
    private LocalDate date;

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", foodPref='" + foodPref + '\'' +
                ", user=" + user +
                ", CurrentDay='" + date + '\'' +
                '}';
    }
}
