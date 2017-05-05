package counterrecord;
import net.sf.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.HashMap;


public class CounterRecord {
    protected ByteBuffer buffer;
    protected String source_ip;
    protected long timestamp;

    public CounterRecord(byte[] bytes, String source_ip, long timestamp) {
        this.source_ip = source_ip;
        this.timestamp = timestamp;
        buffer = ByteBuffer.wrap(bytes);
    }

    public void decode() throws Exception {
        throw new Exception("not implement");
    }

    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("host_ip", source_ip);
        map.put("timestamp", timestamp);
        return map;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = JSONObject.fromObject(getMap());
        return jsonObject.toString(2);
    }

}
