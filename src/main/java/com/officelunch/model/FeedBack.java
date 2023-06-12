package com.officelunch.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause ="is_deleted=false")
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String email;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private long contactDetail;
    private Boolean isDeleted=Boolean.FALSE;
}
