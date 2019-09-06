package com.acgnu.origin.model;


public class ImgBrand {

  private long id;
  private String brand;
  private String site;
  private long priority;
  private String accountName;
  private String accountPass;
  private String accessToken;
  private String accessKey;
  private String uploadUrl;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }


  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }


  public long getPriority() {
    return priority;
  }

  public void setPriority(long priority) {
    this.priority = priority;
  }


  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }


  public String getAccountPass() {
    return accountPass;
  }

  public void setAccountPass(String accountPass) {
    this.accountPass = accountPass;
  }


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }


  public String getUploadUrl() {
    return uploadUrl;
  }

  public void setUploadUrl(String uploadUrl) {
    this.uploadUrl = uploadUrl;
  }

}
