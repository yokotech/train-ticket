package neo4jserver.repositories;

import neo4jserver.domain.Pod;
import neo4jserver.domain.PodResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import java.util.Optional;

public interface PodRepository extends Neo4jRepository<Pod, Long> {

    Optional<Pod> findById(Long id);

    @Query("MATCH (n:Pod) where id(n)={0} return labels(n) as labels, n as node")
    PodResult getPodWithLabels(Long podId);

}
