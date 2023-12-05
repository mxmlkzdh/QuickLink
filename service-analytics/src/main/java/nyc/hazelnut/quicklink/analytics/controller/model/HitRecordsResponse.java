package nyc.hazelnut.quicklink.analytics.controller.model;

import java.util.ArrayList;
import java.util.List;

public final class HitRecordsResponse {

  private List<HitRecord> hitRecords;

  public HitRecordsResponse() {}

  public HitRecordsResponse(List<HitRecord> hitRecords) {
    this.hitRecords = new ArrayList<>(hitRecords);
  }

  public List<HitRecord> getHitRecords() {
    return hitRecords;
  }

}
