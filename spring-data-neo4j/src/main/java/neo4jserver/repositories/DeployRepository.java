package neo4jserver.repositories;

import neo4jserver.domain.Deploy;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DeployRepository extends Neo4jRepository<Deploy, Long> {
}
