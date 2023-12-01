package com.mlkzdh.quicklink.analytics.controller.model;

import java.util.List;

public final class AnalyticsResponse {

  private final UrlRecord urlRecord;
  private final List<HitRecord> hitRecords;

  public AnalyticsResponse(UrlRecord urlRecord, List<HitRecord> hitRecords) {
    this.urlRecord = urlRecord;
    this.hitRecords = hitRecords;
  }

  public UrlRecord getUrlRecord() {
    return urlRecord;
  }

  public List<HitRecord> getHitRecords() {
    return hitRecords;
  }

}
