package counterrecord;


import java.sql.SQLException;
import java.util.HashMap;

public class VirtCounterRecord extends AbstractCounterRecord {
    protected String hostname;

    public VirtCounterRecord(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }


    public void setHostName(String host_name) {
        this.hostname = host_name;
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("hostname", hostname);
        return map;
    }
}
