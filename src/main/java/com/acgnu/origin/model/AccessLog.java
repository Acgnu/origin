package com.acgnu.origin.model;


import java.time.LocalDateTime;

public class AccessLog {
  private long id;
  private String ip;
  private String uri;
  private String device;
  private String os;
  private String broswer;
  private String province;
  private String city;
  private String operator;
  private long frequency;
  private String logHash;
  private LocalDateTime firstAccessTime;
  private LocalDateTime lastAccessTime;

  public AccessLog(){}

  public AccessLog(String ip, String uri, String device, String os, String broswer, long frequency,LocalDateTime firstAccessTime, LocalDateTime lastAccessTime) {
    this.ip = ip;
    this.uri = uri;
    this.device = device;
    this.os = os;
    this.broswer = broswer;
    this.province = province;
    this.city = city;
    this.operator = operator;
    this.frequency = frequency;
    this.firstAccessTime = firstAccessTime;
    this.lastAccessTime = lastAccessTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }


  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }


  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }


  public String getBroswer() {
    return broswer;
  }

  public void setBroswer(String broswer) {
    this.broswer = broswer;
  }


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }


  public long getFrequency() {
    return frequency;
  }

  public void setFrequency(long frequency) {
    this.frequency = frequency;
  }


  public String getLogHash() {
    return logHash;
  }

  public void setLogHash(String logHash) {
    this.logHash = logHash;
  }

  public LocalDateTime getFirstAccessTime() {
    return firstAccessTime;
  }

  public void setFirstAccessTime(LocalDateTime firstAccessTime) {
    this.firstAccessTime = firstAccessTime;
  }

  public LocalDateTime getLastAccessTime() {
    return lastAccessTime;
  }

  public void setLastAccessTime(LocalDateTime lastAccessTime) {
    this.lastAccessTime = lastAccessTime;
  }
}
