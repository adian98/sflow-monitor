package counterrecord;

import java.sql.SQLException;

public class HostCounterRecord extends AbstractCounterRecord {
    public HostCounterRecord(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }

}
