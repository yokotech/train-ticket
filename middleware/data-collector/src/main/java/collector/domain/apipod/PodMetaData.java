package collector.domain.apipod;

public class PodMetaData {

    private String name;

    private String namespace;

    private String selfLink;

    private PodLabels labels;

    public PodMetaData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public PodLabels getLabels() {
        return labels;
    }

    public void setLabels(PodLabels labels) {
        this.labels = labels;
    }
}
