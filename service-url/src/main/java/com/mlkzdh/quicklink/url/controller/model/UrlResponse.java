package com.mlkzdh.quicklink.url.controller.model;

public final class UrlResponse {

  private String key;
  private String shortUrl;
  private String destination;

  private UrlResponse(Builder builder) {
    this.key = builder.key;
    this.shortUrl = builder.shortUrl;
    this.destination = builder.destination;
  }

  public String getKey() {
    return key;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public String getDestination() {
    return destination;
  }

  public static class Builder {

    private String key;
    private String shortUrl;
    private String destination;

    public Builder() {}

    Builder(String key, String shortUrl, String destination) {
      this.key = key;
      this.shortUrl = shortUrl;
      this.destination = destination;
    }

    public Builder key(String key) {
      this.key = key;
      return Builder.this;
    }

    public Builder shortUrl(String shortUrl) {
      this.shortUrl = shortUrl;
      return Builder.this;
    }

    public Builder destination(String destination) {
      this.destination = destination;
      return Builder.this;
    }

    public UrlResponse build() {
      return new UrlResponse(this);
    }

  }

}
