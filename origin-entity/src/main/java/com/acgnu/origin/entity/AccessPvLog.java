package com.acgnu.origin.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "access_pv_log")
public class AccessPvLog extends BaseModel{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "uvid", nullable = false, length = 20)
  private Long uvid;

  @Column(name = "uri", nullable = false, length = 120)
  private String uri;

  @Column(name = "device", length = 20)
  private String device;

  @Column(name = "os", length = 30)
  private String os;

  @Column(name = "broswer", length = 30)
  private String broswer;

  @Column(name = "frequency", nullable = false, length = 11)
  private Integer frequency;

  @Column(name = "update_time", nullable = false)
  private LocalDateTime updateTime;

//  @JSONField(serialize = false) //指定fastjson不序列化此字段
//  @JsonIgnore   //指定jackson 不序列化此字段
//  @ManyToOne(targetEntity = AccessUvLog.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinColumn(name = "uvid")
//  private AccessUvLog accessUvLog;
}
