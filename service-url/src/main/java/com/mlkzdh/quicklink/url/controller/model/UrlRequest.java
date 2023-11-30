package com.mlkzdh.quicklink.url.controller.model;

import jakarta.validation.constraints.NotBlank;

public final class UrlRequest {

  @NotBlank(message = "The 'destination' field must be present.")
  private String destination;

  public UrlRequest() {}

  public UrlRequest(String destination) {
    this.destination = destination;
  }

  public String getDestination() {
    return destination;
  }

}
