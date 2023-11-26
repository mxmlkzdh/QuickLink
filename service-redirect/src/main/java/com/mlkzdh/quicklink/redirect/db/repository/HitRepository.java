package com.mlkzdh.quicklink.redirect.db.repository;

import org.springframework.data.repository.CrudRepository;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;

public interface HitRepository extends CrudRepository<HitRecord, Long> {
}
