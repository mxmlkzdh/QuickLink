package com.mlkzdh.quicklink.url.controller.model;

public final class UrlRequest {

  private String destination;

  public UrlRequest() {}

  public UrlRequest(String destination) {
    this.destination = destination;
  }

  public String getDestination() {
    return destination;
  }

}
