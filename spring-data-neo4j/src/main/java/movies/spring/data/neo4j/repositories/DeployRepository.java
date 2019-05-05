package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Deploy;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DeployRepository extends Neo4jRepository<Deploy, Long> {
}
