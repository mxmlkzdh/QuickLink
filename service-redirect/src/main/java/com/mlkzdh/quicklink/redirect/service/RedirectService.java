package com.mlkzdh.quicklink.redirect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;
import com.mlkzdh.quicklink.redirect.db.repository.HitRepository;

@Service
public final class RedirectService {

  private final HitRepository hitRepository;

  @Autowired
  public RedirectService(HitRepository hitRepository) {
    this.hitRepository = hitRepository;
  }

  public HitRecord save(HitRecord hitRecord) {
    return hitRepository.save(hitRecord);
  }

}
