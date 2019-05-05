package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Pod;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PodRepository extends Neo4jRepository<Pod, Long> {

    Pod findByName(String name);

}
