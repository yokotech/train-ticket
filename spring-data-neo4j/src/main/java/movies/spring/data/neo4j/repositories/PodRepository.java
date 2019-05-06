package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Pod;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface PodRepository extends Neo4jRepository<Pod, Long> {

    Pod findByName(String name);

    Optional<Pod> findById(Long id);

}
