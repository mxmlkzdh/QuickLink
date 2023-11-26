package com.mlkzdh.quicklink.url.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlkzdh.quicklink.url.controller.model.UrlRequest;
import com.mlkzdh.quicklink.url.controller.model.UrlResponse;
import com.mlkzdh.quicklink.url.db.model.UrlRecord;
import com.mlkzdh.quicklink.url.service.UrlService;

@AutoConfigureMockMvc
@SpringBootTest
class UrlControllerTest {

  private static final String DESTINATION_URL = "https://www.google.com";
  private static final String KEY = "aaaaab";
  private static final Long ID = 1L;
  private static final String SHORT_URL = "http://dev.local/u/" + KEY;
  private static final String ENDPOINT = "/api/v1/url";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UrlService urlService;

  @Test
  void save_requestIsNull_returnBadRequest() throws Exception {
    mockMvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void save_destinationIsNull_returnBadRequest() throws Exception {
    UrlRequest urlRequest = new UrlRequest();

    mockMvc.perform(
        post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void save_destinationIsEmpty_returnBadRequest() throws Exception {
    UrlRequest urlRequest = new UrlRequest("");

    mockMvc.perform(
        post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void save_returnUrlResponse() throws Exception {
    UrlRecord urlRecord = new UrlRecord.Builder()
        .id(ID)
        .destination(DESTINATION_URL)
        .build();
    UrlRequest urlRequest = new UrlRequest(DESTINATION_URL);
    UrlResponse urlResponse = new UrlResponse.Builder()
        .key(KEY)
        .shortUrl(SHORT_URL)
        .destination(DESTINATION_URL)
        .build();
    when(urlService.save(any(UrlRecord.class))).thenReturn(urlRecord);

    mockMvc
        .perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequest)))
        .andExpect(status().isCreated())
        .andExpect(
            content().string(objectMapper.writeValueAsString(urlResponse)));
  }

  @Test
  void find_keyDoesNotExist_returnNotFound() throws Exception {
    when(urlService.find(ID)).thenReturn(Optional.empty());
    mockMvc.perform(get(ENDPOINT + "/{id}", String.valueOf(ID))).andExpect(status().isNotFound());
  }

  @Test
  void find_returnUrlRecord() throws Exception {
    UrlRecord urlRecord = new UrlRecord.Builder()
        .id(ID)
        .destination(DESTINATION_URL)
        .build();
    when(urlService.find(ID)).thenReturn(Optional.of(urlRecord));

    mockMvc.perform(get(ENDPOINT + "/{id}", String.valueOf(ID)))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(urlRecord)));
  }

}
