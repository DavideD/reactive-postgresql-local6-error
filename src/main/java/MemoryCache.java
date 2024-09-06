import static io.quarkus.scheduler.Scheduled.ConcurrentExecution.SKIP;
import static java.lang.String.join;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.reactive.mutiny.Mutiny;

@ApplicationScoped
public class MemoryCache {

  private final Mutiny.SessionFactory sf;

  private final Map<UUID, Code> codeCache = new ConcurrentHashMap<>();

  public MemoryCache(Mutiny.SessionFactory sf) {
    this.sf = sf;
  }

  @Startup
  public void buildCache() {
    sf
      .withSession(session ->
        session
          .createQuery("from Code c", Code.class)
          .getResultList()
          .chain(list -> {
            list.forEach(code ->
              codeCache.put(code.getResourceIdentifier(), code)
            );
            return Uni.createFrom().item(list);
          })
      )
      .onFailure()
      .transform(failure ->
        new IllegalStateException("Failed to complete build of cache", failure)
      )
      .invoke(() -> Log.infof("Built cache with %d codes", codeCache.size()))
      .subscribe()
      .with(item -> Log.debugf("Done with startat event %s", item));
  }

  @Scheduled(every = "5s", delayed = "5s", concurrentExecution = SKIP)
  public Uni<Void> updateCache() {
    var codesUUID = codeCache.keySet().stream().toList();
    var findRemovedCodes = new StringBuilder("(values"); // https://stackoverflow.com/questions/17209460/select-those-not-found-in-in-list
    // var findRemovedCodes = new StringBuilder("(select tmp.resource_id from (values("); // https://stackoverflow.com/questions/17209460/select-those-not-found-in-in-list
    for (int i = 0; i < codeCache.size(); i++) {
      findRemovedCodes.append("('%s'\\:\\:uuid)".formatted(codesUUID.get(i)));
      if (i < codeCache.size() - 1) {
        findRemovedCodes.append(",");
      }
    }
    // findRemovedCodes.append(") as tmp(resource_id)");
    findRemovedCodes.append("except all select t.resource_id from code t)");
    var findAddedCodes =
      "union select x.resource_id from code x where x.resource_id not in :resource_id";
    var query = join(" ", findRemovedCodes.toString(), findAddedCodes);
    return sf
      .withSession(session ->
        session
          .createNativeQuery(query, UUID.class)
          .setParameter("resource_id", codesUUID)
          .getResultList()
      )
      .replaceWithVoid();
  }
}
