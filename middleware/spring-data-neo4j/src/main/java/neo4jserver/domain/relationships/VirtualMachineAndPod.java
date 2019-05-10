package neo4jserver.domain.relationships;

import neo4jserver.domain.entities.Pod;
import neo4jserver.domain.entities.VirtualMachine;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "DEPLOY_ON")
public class VirtualMachineAndPod {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Pod pod;

    @Property(name="relation")
    private String relation;

    @Property(name="className")
    private String className = this.getClass().toString();

    @EndNode
    private VirtualMachine virtualMachine;

    public VirtualMachineAndPod() {
    }

    public VirtualMachineAndPod(Pod pod, String relation, VirtualMachine virtualMachine) {
        this.pod = pod;
        this.relation = relation;
        this.virtualMachine = virtualMachine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pod getPod() {
        return pod;
    }

    public void setPod(Pod pod) {
        this.pod = pod;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }
}

