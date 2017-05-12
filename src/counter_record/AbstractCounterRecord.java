package counter_record;
import log.LOG;
import net.sf.json.JSONObject;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;


class AbstractCounterRecord {
    protected ByteBuffer buffer;
    protected String host_ip;
    protected long timestamp;

    public AbstractCounterRecord(byte[] bytes, String host_ip, long timestamp) {
        this.host_ip = host_ip;
        this.timestamp = timestamp;
        buffer = ByteBuffer.wrap(bytes);
    }

    protected AbstractCounterRecord() {
        //do nothing
    }

    public void decode() throws Exception {
        LOG.ERROR("not implement method");
    }

    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("host_ip", host_ip);
        map.put("timestamp", timestamp);
        return map;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = JSONObject.fromObject(getMap());
        return jsonObject.toString(2);
    }

    public void saveToDb(Connection conn) throws Exception {
        LOG.ERROR("not implement method");
    }

}

