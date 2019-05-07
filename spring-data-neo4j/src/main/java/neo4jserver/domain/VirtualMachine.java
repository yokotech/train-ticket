package neo4jserver.domain;

import org.neo4j.ogm.annotation.*;

@NodeEntity(label="VirtualMachine")
public class VirtualMachine extends GraphNode{

    @Property(name="memory")
    private int memory;

    @Property(name="CPU")
    private double CPU;

    public VirtualMachine() {
    }

    public VirtualMachine(String name, int memory, double CPU) {
        super(name);
        this.memory = memory;
        this.CPU = CPU;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public double getCPU() {
        return CPU;
    }

    public void setCPU(double CPU) {
        this.CPU = CPU;
    }
}
