package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.VirtualMachine;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VirtualMachineRepository extends Neo4jRepository<VirtualMachine, Long> {

    VirtualMachine findByName(String name);

}
