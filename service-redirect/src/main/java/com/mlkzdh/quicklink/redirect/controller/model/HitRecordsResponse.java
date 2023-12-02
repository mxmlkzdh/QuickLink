package com.mlkzdh.quicklink.redirect.controller.model;

import java.util.List;
import com.google.common.collect.Lists;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;

public final class HitRecordsResponse {

  private final List<HitRecord> hitRecords;

  public HitRecordsResponse(List<HitRecord> hitRecords) {
    this.hitRecords = Lists.newArrayList(hitRecords);
  }

  public List<HitRecord> getHitRecords() {
    return hitRecords;
  }

  public static HitRecordsResponse empty() {
    return new HitRecordsResponse(Lists.newArrayList());
  }

}
