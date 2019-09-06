package com.acgnu.origin.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Image {

  private long id;
  private String title;
  private long brandId;
  private String localUrl;
  private String netUrl;
  private String spareUrl;
  private LocalDateTime createTime;
  private LocalDateTime expireTime;
  private long isSync;
  private LocalDateTime syncTime;
  private long valid;
  private String remark;
}
