package com.mlkzdh.quicklink.redirect.controller.model;

public final class UrlRecord {

  private Long id;
  private String destination;

  public UrlRecord() { /* Required for JSON */ }

  public Long getId() {
    return id;
  }

  public String getDestination() {
    return destination;
  }

}
