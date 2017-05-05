package counterrecord;


import config.Config;

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


    public HostDiskIoInfo(byte[] bytes) {
        super(bytes);
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
}
