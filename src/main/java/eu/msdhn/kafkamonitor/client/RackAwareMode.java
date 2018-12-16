package eu.msdhn.kafkamonitor.client;

public enum RackAwareMode {

    DISABLED(kafka.admin.RackAwareMode.Disabled$.MODULE$),
    ENFORCED(kafka.admin.RackAwareMode.Disabled$.MODULE$),
    SAFE(kafka.admin.RackAwareMode.Disabled$.MODULE$);

    private kafka.admin.RackAwareMode mode;

    private RackAwareMode(kafka.admin.RackAwareMode rackAwareMode) {
        this.mode = rackAwareMode;
    }

    public kafka.admin.RackAwareMode mode() {
        return this.mode;
    }
}
