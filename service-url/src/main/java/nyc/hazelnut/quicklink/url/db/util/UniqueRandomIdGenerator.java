package nyc.hazelnut.quicklink.url.db.util;

import java.util.concurrent.ThreadLocalRandom;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import nyc.hazelnut.quicklink.url.db.model.UrlRecord;

public final class UniqueRandomIdGenerator implements IdentifierGenerator {

  private static final long UPPER_BOUND = 56800235584L; // 62^6 = 6-character String in base62

  @Override
  public Object generate(SharedSessionContractImplementor session, Object object) {
    Long randomId = ThreadLocalRandom.current().nextLong(UPPER_BOUND);
    try (SessionImplementor sessionImplementor = session.getFactory().openSession()) {
      while (sessionImplementor.get(UrlRecord.class, randomId) != null) {
        randomId = ThreadLocalRandom.current().nextLong(UPPER_BOUND);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return randomId;
  }

}
