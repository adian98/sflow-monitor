package counterrecord;

import config.Config;

import java.util.HashMap;

public class HostNetIoInfo extends HostCounterRecord {
    private long bytes_in;     /* total bytes in */
    private long packets_in;   /* total packets in */
    private long errs_in;      /* total errors in */
    private long drops_in;     /* total drops in */
    private long bytes_out;    /* total bytes out */
    private long packets_out;  /* total packets out */
    private long errs_out;     /* total errors out */
    private long drops_out;    /* total drops out */

    private HostNetIoInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    static public HostNetIoInfo fromBytes(byte[] bytes, String sourceIP, long timestamp)
            throws Exception {
        HostNetIoInfo info = new HostNetIoInfo(bytes, sourceIP, timestamp);
        info.decode();
        return info;
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

    static public String schema() {
        return "CREATE TABLE host_net (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "bytes_in INTEGER, " +
                "packets_in INTEGER, " +
                "errs_in INTEGER, " +
                "drops_in INTEGER, " +
                "bytes_out INTEGER, " +
                "packets_out INTEGER, " +
                "errs_out INTEGER, " +
                "drops_out INTEGER, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip) );";
    }
}
