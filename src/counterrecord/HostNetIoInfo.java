package counterrecord;

import config.Config;

import java.util.HashMap;

public class HostNetIoInfo extends CounterRecord {
    private long bytes_in;     /* total bytes in */
    private long packets_in;   /* total packets in */
    private long errs_in;      /* total errors in */
    private long drops_in;     /* total drops in */
    private long bytes_out;    /* total bytes out */
    private long packets_out;  /* total packets out */
    private long errs_out;     /* total errors out */
    private long drops_out;    /* total drops out */

    public HostNetIoInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    @Override
    public void decode() throws Exception {
        bytes_in = buffer.getLong();
        packets_in = Utils.bufferGetUint32(buffer);
        errs_in = Utils.bufferGetUint32(buffer);
        drops_in = Utils.bufferGetUint32(buffer);

        bytes_out = buffer.getLong();
        packets_out = Utils.bufferGetUint32(buffer);;
        errs_out = Utils.bufferGetUint32(buffer);
        drops_out = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("bytes_in", bytes_in);
        map.put("packets_in", packets_in);
        map.put("errs_in", errs_in);
        map.put("drops_in", drops_in);
        map.put("bytes_out", bytes_out);
        map.put("packets_out", packets_out);
        map.put("errs_out",errs_out);
        map.put("drops_out", drops_out);
        return map;
    }
}
