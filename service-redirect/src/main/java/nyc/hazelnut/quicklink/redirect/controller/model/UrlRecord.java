package nyc.hazelnut.quicklink.redirect.controller.model;

public final class UrlRecord {

  private Long id;
  private String destination;

  public UrlRecord() {}

  private UrlRecord(Builder builder) {
    this.id = builder.id;
    this.destination = builder.destination;
  }

  public Long getId() {
    return id;
  }

  public String getDestination() {
    return destination;
  }

  public static class Builder {

    private Long id;
    private String destination;

    public Builder() {}

    Builder(Long id, String destination) {
      this.id = id;
      this.destination = destination;
    }

    public Builder id(Long id) {
      this.id = id;
      return Builder.this;
    }

    public Builder destination(String destination) {
      this.destination = destination;
      return Builder.this;
    }

    public UrlRecord build() {
      return new UrlRecord(this);
    }

  }

}
