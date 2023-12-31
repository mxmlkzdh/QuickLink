package nyc.hazelnut.quicklink.url.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public final class UrlRequest {

  @NotBlank(message = "The 'destination' field must be present.")
  @Pattern(
      regexp = "^(?:(?:(?:https?|ftp):)?\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z0-9\\x{00a1}-\\x{ffff}][a-z0-9\\x{00a1}-\\x{ffff}_-]{0,62})?[a-z0-9\\x{00a1}-\\x{ffff}]\\.)+(?:[a-z\\x{00a1}-\\x{ffff}]{2,}\\.?))(?::\\d{2,5})?(?:[/?#]\\S*)?$")
  private String destination;

  public UrlRequest() {}

  public UrlRequest(String destination) {
    this.destination = destination;
  }

  public String getDestination() {
    return destination;
  }

}
