package com.acgnu.origin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "img_brand")
public class ImgBrand{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "brand", nullable = false, length = 30)
  private String brand;

  @Column(name = "site", nullable = false, length = 100)
  private String site;

  @Column(name = "priority", nullable = false, length = 1)
  private Short priority;

  @Column(name = "account_name", length = 25)
  private String accountName;

  @Column(name = "account_pass", length = 60)
  private String accountPass;

  @Column(name = "access_token", length = 120)
  private String accessToken;

  @Column(name = "access_key", length = 60)
  private String accessKey;

  @Column(name = "upload_url", length = 120)
  private String uploadUrl;
}
