package neo4jserver.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "DEPLOY_ON")
public class Deploy {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Pod pod;

    @Property(name="title")
    private String title;

    @Property(name="className")
    private String className = this.getClass().toString();

    @EndNode
    private VirtualMachine virtualMachine;

    public Deploy() {
    }

    public Deploy(Pod pod, String title, VirtualMachine virtualMachine) {
        this.pod = pod;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public String getClassName() {
        return className;
    }
}

