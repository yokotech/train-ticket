package collector.domain.apipod;

public class Pod {

    private PodMetaData metadata;

    private PodSpec spec;

    private PodStatus status;

    public Pod() {
    }

    public PodMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(PodMetaData metadata) {
        this.metadata = metadata;
    }

    public PodSpec getSpec() {
        return spec;
    }

    public void setSpec(PodSpec spec) {
        this.spec = spec;
    }

    public PodStatus getStatus() {
        return status;
    }

    public void setStatus(PodStatus status) {
        this.status = status;
    }
}
