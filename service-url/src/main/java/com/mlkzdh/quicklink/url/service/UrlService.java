package com.mlkzdh.quicklink.url.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mlkzdh.quicklink.url.db.model.UrlRecord;
import com.mlkzdh.quicklink.url.db.repository.UrlRepository;

@Service
public final class UrlService {

  private final UrlRepository urlRepository;

  @Autowired
  public UrlService(UrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  public UrlRecord save(UrlRecord urlRecord) {
    return urlRepository.save(urlRecord);
  }

  public Optional<UrlRecord> find(Long id) {
    return urlRepository.findById(id);
  }

}
