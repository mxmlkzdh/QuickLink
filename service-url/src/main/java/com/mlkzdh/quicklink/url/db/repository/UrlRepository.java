package com.mlkzdh.quicklink.url.db.repository;

import org.springframework.data.repository.CrudRepository;
import com.mlkzdh.quicklink.url.db.model.UrlRecord;

public interface UrlRepository extends CrudRepository<UrlRecord, Long> {
}
