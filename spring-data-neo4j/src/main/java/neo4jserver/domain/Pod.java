package neo4jserver.domain;

import org.neo4j.ogm.annotation.*;

@NodeEntity(label="Pod")
public class Pod extends GraphNode{

    @Property(name="numberOfContainers")
    private int numberOfContainers;

    public Pod() {
        super();
    }

    public Pod(String name, int numberOfContainers) {
        super(name);
        this.numberOfContainers = numberOfContainers;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }
}
