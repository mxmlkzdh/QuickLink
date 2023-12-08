package nyc.hazelnut.quicklink.analytics.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nyc.hazelnut.quicklink.analytics.controller.model.AnalyticsResponse;
import nyc.hazelnut.quicklink.analytics.controller.model.HitRecord;
import nyc.hazelnut.quicklink.analytics.controller.model.UrlRecord;
import nyc.hazelnut.quicklink.analytics.service.AnalyticsService;

@AutoConfigureMockMvc
@SpringBootTest
class AnalyticsControllerTest {

  private static final String DESTINATION = "https://google.com";
  private static final Long ID = 1L;
  private static final String KEY = "aaaaab";
  private static final String KEY_INVALID = "aaaab";
  private static final String ENDPOINT = "/api/v1/analytics/{key}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private AnalyticsService analyticsService;

  @Test
  void analytics_keyIsNotValid_returnBadRequest() throws Exception {
    mockMvc.perform(get(ENDPOINT, KEY_INVALID)).andExpect(status().isBadRequest());
  }

  @Test
  void analytics_keyDoesNotExist_returnNotFound() throws Exception {
    when(analyticsService.findUrlRecord(anyString()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

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
        .thenReturn(urlRecord);
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
        .thenReturn(urlRecord);
    when(analyticsService.findHitRecords(anyString()))
        .thenReturn(hitRecords);

    mockMvc.perform(get(ENDPOINT, KEY))
        .andExpect(status().isOk())
        .andExpect(content().string(objectMapper.writeValueAsString(analyticsResponse)));
  }
}
