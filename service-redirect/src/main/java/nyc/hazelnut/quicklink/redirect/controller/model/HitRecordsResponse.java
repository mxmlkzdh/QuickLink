package nyc.hazelnut.quicklink.redirect.controller.model;

import java.util.ArrayList;
import java.util.List;
import nyc.hazelnut.quicklink.redirect.db.model.HitRecord;

public final class HitRecordsResponse {

  private final List<HitRecord> hitRecords;

  public HitRecordsResponse(List<HitRecord> hitRecords) {
    this.hitRecords = new ArrayList<>(hitRecords);
  }

  public List<HitRecord> getHitRecords() {
    return hitRecords;
  }

  public static HitRecordsResponse empty() {
    return new HitRecordsResponse(new ArrayList<>());
  }

}
