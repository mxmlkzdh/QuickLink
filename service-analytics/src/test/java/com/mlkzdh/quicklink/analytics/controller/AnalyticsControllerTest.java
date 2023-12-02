package com.mlkzdh.quicklink.analytics.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlkzdh.quicklink.analytics.controller.model.AnalyticsResponse;
import com.mlkzdh.quicklink.analytics.controller.model.HitRecord;
import com.mlkzdh.quicklink.analytics.controller.model.UrlRecord;
import com.mlkzdh.quicklink.analytics.service.AnalyticsService;

@AutoConfigureMockMvc
@SpringBootTest
class AnalyticsControllerTest {

  private static final String DESTINATION = "https://google.com";
  private static final Long ID = 1L;
  private static final String KEY = "aaaaab";
  private static final String ENDPOINT = "/api/v1/analytics/{key}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AnalyticsService analyticsService;

  @Test
  void analytics_keyDoesNotExist_returnNotFound() throws Exception {
    when(analyticsService.findUrlRecord(anyString()))
        .thenReturn(Optional.empty());

    mockMvc.perform(get(ENDPOINT, KEY)).andExpect(status().isNotFound());
  }

  @Test
  void analytics_emptyHitRecords_returnResponse() throws Exception {
    UrlRecord urlRecord = new UrlRecord.Builder()
        .id(ID)
        .destination(DESTINATION)
        .build();
    List<HitRecord> hitRecords = new ArrayList<>();
    AnalyticsResponse analyticsResponse = new AnalyticsResponse(urlRecord, hitRecords);
    when(analyticsService.findUrlRecord(anyString()))
        .thenReturn(Optional.of(urlRecord));
    when(analyticsService.findHitRecords(anyString()))
        .thenReturn(hitRecords);

    mockMvc.perform(get(ENDPOINT, KEY))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(analyticsResponse)));
  }

  @Test
  void analytics_returnResponse() throws Exception {
    UrlRecord urlRecord = new UrlRecord.Builder()
        .id(ID)
        .destination(DESTINATION)
        .build();
    List<HitRecord> hitRecords = new ArrayList<>();
    hitRecords.add(new HitRecord.Builder().urlRecordId(ID).build());
    AnalyticsResponse analyticsResponse = new AnalyticsResponse(urlRecord, hitRecords);
    when(analyticsService.findUrlRecord(anyString()))
        .thenReturn(Optional.of(urlRecord));
    when(analyticsService.findHitRecords(anyString()))
        .thenReturn(hitRecords);

    mockMvc.perform(get(ENDPOINT, KEY))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(analyticsResponse)));
  }
}
