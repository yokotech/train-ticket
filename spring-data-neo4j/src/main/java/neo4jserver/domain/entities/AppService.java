package neo4jserver.domain.entities;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label="AppService")
public class AppService extends GraphNode {

    @Property(name="IP")
    private String IP;

    @Property(name="type")
    private String type; //NodePort Or Some others

    @Property(name="port")
    private int port;

    public AppService() {
        super();
    }

    public AppService(String name, String IP, String type, int port) {
        super(name);
        this.IP = IP;
        this.type = type;
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
