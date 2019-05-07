package neo4jserver.repositories;

import neo4jserver.domain.VirtualMachine;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface VirtualMachineRepository extends Neo4jRepository<VirtualMachine, Long> {

    Optional<VirtualMachine> findById(Long id);

}
