package com.acgnu.origin.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "image")
public class Image extends BaseModel{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = 40)
  private String title;

  @Column(name = "brand_id", nullable = false, length = 11)
  private Integer brandId;

  @Column(name = "local_url", length = 160)
  private String localUrl;

  @Column(name = "net_url", nullable = false)
  private String netUrl;

  @Column(name = "spare_url")
  private String spareUrl;

  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;

  @Column(name = "expire_time")
  private LocalDateTime expireTime;

  @Column(name = "is_sync", nullable = false, length = 1)
  private Byte isSync;

  @Column(name = "sync_time")
  private LocalDateTime syncTime;

  @Column(name = "valid", nullable = false, length = 1)
  private Byte valid;

  @Column(name = "md5", length = 64)
  private String md5;

  @Column(name = "remark", length = 60)
  private String remark;
}
