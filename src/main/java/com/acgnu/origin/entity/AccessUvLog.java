package com.acgnu.origin.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_uv_log")
public class AccessUvLog extends BaseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ip", nullable = false, length = 50)
  private String ip;

  @Column(name = "province", length = 20)
  private String province;

  @Column(name = "city", length = 30)
  private String city;

  @Column(name = "isp", length = 20)
  private String isp;

  @Column(name = "total_access", nullable = false, length = 20)
  private Long totalAccess;

  @Column(name = "first_access_time", nullable = false)
  private LocalDateTime firstAccessTime;

  @Column(name = "last_access_time", nullable = false)
  private LocalDateTime lastAccessTime;

  @OneToMany(mappedBy = "uvid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<AccessPvLog> accessPvLogs;

//  public AccessUvLog(String ip, String province, String city, String operator, Long totalAccess, LocalDateTime firstAccessTime, LocalDateTime lastAccessTime) {
//    this.ip = ip;
//    this.province = province;
//    this.city = city;
//    this.operator = operator;
//    this.totalAccess = totalAccess;
//    this.firstAccessTime = firstAccessTime;
//    this.lastAccessTime = lastAccessTime;
//  }
}
