package com.mlkzdh.quicklink.redirect.db.model;

import java.util.Date;
import java.util.Locale;
import org.hibernate.annotations.CreationTimestamp;
import com.mlkzdh.quicklink.redirect.db.config.DatabaseConfig.Table.HitRecords;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = HitRecords.NAME)
@Table(name = HitRecords.NAME)
public final class HitRecord {

  @Id
  @GeneratedValue
  @Column(name = HitRecords.Column.ID)
  private Long id;

  @Column(name = HitRecords.Column.URL_RECORD_ID)
  private Long urlRecordId;

  @Column(name = HitRecords.Column.IP)
  private String ip;

  @Column(name = HitRecords.Column.USER_AGENT)
  private String userAgent;

  @Column(name = HitRecords.Column.REFERER)
  private String referer;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(name = HitRecords.Column.TIMESTAMP)
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
