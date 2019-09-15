package com.acgnu.origin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "privilege")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pname", nullable = false, length = 30)
    private String pname;

    @Column(name = "uri", nullable = false, length = 120)
    private String uri;

    @Column(name = "remark", length = 50)
    private String remark;

}
