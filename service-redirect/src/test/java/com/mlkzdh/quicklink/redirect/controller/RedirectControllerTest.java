package com.mlkzdh.quicklink.redirect.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import com.mlkzdh.quicklink.redirect.controller.model.UrlRecord;
import com.mlkzdh.quicklink.redirect.service.RedirectService;

@AutoConfigureMockMvc
@SpringBootTest
class RedirectControllerTest {

  private static final String DESTINATION = "https://google.com";
  private static final Long ID = 1L;
  private static final String KEY = "aaaaab";
  private static final String ENDPOINT = "/{key}";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RedirectService redirectService;

  @Test
  void redirect_keyDoesNotExist_returnNotFound() throws Exception {
    when(redirectService.findUrlRecord(anyString()))
        .thenReturn(Optional.empty());

    mockMvc.perform(get(ENDPOINT, KEY)).andExpect(status().isNotFound());
  }

  @Test
  void redirect_returnResponse() throws Exception {
    UrlRecord urlRecord = new UrlRecord.Builder()
        .id(ID)
        .destination(DESTINATION)
        .build();
    when(redirectService.findUrlRecord(anyString()))
        .thenReturn(Optional.of(urlRecord));

    mockMvc.perform(get(ENDPOINT, KEY))
        .andExpect(status().isMovedPermanently())
        .andExpect(header().stringValues(HttpHeaders.CACHE_CONTROL, "no-cache, no-store"))
        .andExpect(header().stringValues(HttpHeaders.LOCATION, DESTINATION));
  }

}
