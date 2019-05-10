package neo4jserver.repositories;

import neo4jserver.domain.relationships.AppServiceAndPod;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface AppServiceAndPodRepository extends Neo4jRepository<AppServiceAndPod, Long> {

    Optional<AppServiceAndPod> findById(Long id);

}
