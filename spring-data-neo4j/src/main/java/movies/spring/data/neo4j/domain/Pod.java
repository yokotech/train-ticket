package movies.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.*;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Pod {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name="name")
    private String name;

    @Property(name="className")
    private String className = this.getClass().toString();

    @Labels
    private Set<String> labels = new HashSet<>();

    public Pod() {
    }

    public Pod(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }


    public Set<String> getLabels() {
        return labels;
    }

    public void addLabel(String name) {
        this.labels.add(name);
    }
}
