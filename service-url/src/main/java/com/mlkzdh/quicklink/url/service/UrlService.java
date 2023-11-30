package com.mlkzdh.quicklink.url.service;

import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mlkzdh.quicklink.url.db.model.UrlRecord;
import com.mlkzdh.quicklink.url.db.repository.UrlRepository;

@Service
public class UrlService {

  private static final Log LOG = LogFactory.getLog(UrlService.class);
  private final UrlRepository urlRepository;

  @Autowired
  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public UrlRecord save(UrlRecord urlRecord) {
    UrlRecord savedUrlRecord = urlRepository.save(urlRecord);
    LOG.info(String.format("UrlRecord saved: %s", savedUrlRecord));
    return savedUrlRecord;
  }

  public Optional<UrlRecord> find(Long id) {
    Optional<UrlRecord> urlRecord = urlRepository.findById(id);
    urlRecord.ifPresentOrElse(url -> LOG.info(String.format("UrlRecord found: %d", id)),
        () -> LOG.warn(String.format("UrlRecord NOT found: %d", id)));
    return urlRecord;
  }

}
