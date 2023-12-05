package nyc.hazelnut.quicklink.url.db.repository;

import org.springframework.data.repository.CrudRepository;
import nyc.hazelnut.quicklink.url.db.model.UrlRecord;

public interface UrlRepository extends CrudRepository<UrlRecord, Long> {
}
