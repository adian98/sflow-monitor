package counterrecord;


import config.Config;

import java.util.HashMap;

public class HostDiskIoInfo extends CounterRecord {
    private long  disk_total;     /* total disk size in bytes */
    private long  disk_free;      /* total disk free in bytes */
    private float part_max_used;  /* utilization of most utilized partition */
    private long  reads;          /* reads issued */
    private long  bytes_read;     /* bytes read */
    private long  read_time;      /* read time (ms) */
    private long  writes;         /* writes completed */
    private long  bytes_written;  /* bytes written */
    private long  write_time;     /* write time (ms) */

    public HostDiskIoInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    @Override
    public void decode() throws Exception {
        disk_total = buffer.getLong();
        disk_free = buffer.getLong();
        part_max_used = buffer.getInt() / 100;
        reads = Utils.bufferGetUint32(buffer);
        bytes_read = buffer.getLong();
        read_time = Utils.bufferGetUint32(buffer);
        writes = Utils.bufferGetUint32(buffer);
        bytes_written = buffer.getLong();
        write_time = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("disk_total", disk_total);
        map.put("disk_free", disk_free);
        map.put("part_max_used",part_max_used);
        map.put("reads", reads);
        map.put("bytes_read", bytes_read);
        map.put("read_time", read_time);
        map.put("writes", writes);
        map.put("bytes_written", bytes_written);
        map.put("write_time", write_time);
        return map;
    }

    static public String schema() {
        return "CREATE TABLE host_disk_io (" +
                "ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "disk_total INTEGER, " +
                "disk_free INTEGER, "  +
                "part_max_used READ, " +
                "reads INTEGER, "  +
                "bytes_read INTEGER, "  +
                "read_time INTEGER, "  +
                "writes INTEGER, "  +
                "bytes_written INTEGER, "  +
                "write_time INTEGER, "  +
                "PRIMARY KEY(ip, timestamp)," +
                "FOREIGN KEY(ip) REFERENCES host_description(ip) );";
    }
}
