package neo4jserver.repositories;

import neo4jserver.domain.Pod;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface PodRepository extends Neo4jRepository<Pod, Long> {

    Optional<Pod> findById(Long id);

}
