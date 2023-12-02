package com.mlkzdh.quicklink.redirect.db.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;

public interface HitRepository extends CrudRepository<HitRecord, Long> {
  Optional<List<HitRecord>> findAllByUrlRecordId(long urlRecordId);
}
