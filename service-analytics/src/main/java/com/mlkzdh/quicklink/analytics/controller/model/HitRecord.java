package com.mlkzdh.quicklink.analytics.controller.model;

import java.util.Date;
import java.util.Locale;

public final class HitRecord {

  private Long id;
  private Long urlRecordId;
  private String ip;
  private String userAgent;
  private String referer;
  private Date timestamp;

  public HitRecord() {}

  private HitRecord(Builder builder) {
    this.id = builder.id;
    this.urlRecordId = builder.urlRecordId;
    this.ip = builder.ip;
    this.userAgent = builder.userAgent;
    this.referer = builder.referer;
    this.timestamp = builder.timestamp;
  }

  public Long getId() {
    return id;
  }

  public Long getUrlRecordId() {
    return urlRecordId;
  }

  public String getIp() {
    return ip;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public String getReferer() {
    return referer;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return String.format(Locale.getDefault(), "[id: %d, urlRecordId: %d, ip: %s, timestamp: %s]",
        id, urlRecordId, ip, timestamp);
  }

  public static class Builder {

    private Long id;
    private Long urlRecordId;
    private String ip;
    private String userAgent;
    private String referer;
    private Date timestamp;

    public Builder() {}

    Builder(Long id, Long urlRecordId, String ip, String userAgent, String referer,
        Date timestamp) {
      this.id = id;
      this.urlRecordId = urlRecordId;
      this.ip = ip;
      this.userAgent = userAgent;
      this.referer = referer;
      this.timestamp = timestamp;
    }

    public Builder id(Long id) {
      this.id = id;
      return Builder.this;
    }

    public Builder urlRecordId(Long urlRecordId) {
      this.urlRecordId = urlRecordId;
      return Builder.this;
    }

    public Builder ip(String ip) {
      this.ip = ip;
      return Builder.this;
    }

    public Builder userAgent(String userAgent) {
      this.userAgent = userAgent;
      return Builder.this;
    }

    public Builder referer(String referer) {
      this.referer = referer;
      return Builder.this;
    }

    public Builder timestamp(Date timestamp) {
      this.timestamp = timestamp;
      return Builder.this;
    }

    public HitRecord build() {
      return new HitRecord(this);
    }

  }

}
