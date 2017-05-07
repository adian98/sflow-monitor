package counterrecord;


import java.sql.SQLException;

public class VirtCounterRecord extends AbstractCounterRecord {
    protected String host_name;

    public VirtCounterRecord(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }

    public void setHostName(String host_name) {
        this.host_name = host_name;
    }
}
