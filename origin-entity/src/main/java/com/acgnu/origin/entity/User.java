package com.acgnu.origin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uname", length = 30, nullable = false, unique = true)
    private String uname;

    @Column(name = "upass", length = 60, nullable = false)
    private String upass;

    @Column(name = "salt", length = 60, nullable = false)
    private String salt;

    @Column(name = "nick", length = 60)
    private String nick;

    @Column(name = "locked", length = 1, nullable = false)
    private boolean isLocked;

    @Column(name = "createby", length = 11, nullable = false)
    private int createby;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;
}
