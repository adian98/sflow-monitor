package counter_record;

public class HostCounterRecord extends AbstractCounterRecord {
    public HostCounterRecord(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }

    protected HostCounterRecord() {
        super();
    }
}
