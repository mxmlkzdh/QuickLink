package com.mlkzdh.quicklink.redirect.network;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

public final class RedirectResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    return response.getStatusCode() == HttpStatus.NOT_FOUND;
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

}
