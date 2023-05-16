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
    @OneToOne
    private User user;
    private LocalDate date1;
    private LocalDate date2;
    private LocalDate date3;
    private Boolean isPresent = Boolean.FALSE;


    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", user=" + user +
                ", date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", date3='" + date3 + '\'' +
                ", isPresent=" + isPresent +
                '}';
    }
}
