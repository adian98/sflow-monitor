package counterrecord;

import config.Config;

import java.util.HashMap;

/**
 * Created by cloud on 17-5-3.
 */
public class VirtNetIoInfo extends CounterRecord {

    private long vnio_bytes_in;    /* total bytes received */
    private long vnio_packets_in;  /* total packets received */
    private long vnio_errs_in;     /* total receive errors */
    private long vnio_drops_in;    /* total receive drops */
    private long vnio_bytes_out;   /* total bytes transmitted */
    private long vnio_packets_out; /* total packets transmitted */
    private long vnio_errs_out;    /* total transmit errors */
    private long vnio_drops_out;   /* total transmit drops */

    public VirtNetIoInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    @Override
    public void decode() throws Exception {

        vnio_bytes_in = buffer.getLong();
        vnio_packets_in  = Utils.bufferGetUint32(buffer);
        vnio_errs_in = Utils.bufferGetUint32(buffer);
        vnio_drops_in = Utils.bufferGetUint32(buffer);
        vnio_bytes_out = buffer.getLong();
        vnio_packets_out = Utils.bufferGetUint32(buffer);
        vnio_errs_out = Utils.bufferGetUint32(buffer);
        vnio_drops_out = Utils.bufferGetUint32(buffer);
    }


    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vnio_bytes_in", vnio_bytes_in);
        map.put("vnio_packets_in", vnio_packets_in);
        map.put("vnio_errs_in", vnio_errs_in);
        map.put("vnio_drops_in", vnio_drops_in);
        map.put("vnio_bytes_out", vnio_bytes_out);
        map.put("vnio_packets_out", vnio_packets_out);
        map.put("vnio_errs_out", vnio_errs_out);
        map.put("vnio_drops_out", vnio_drops_out);
        return map;
    }
}