package counterrecord;


import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class HostDiskIoInfo extends HostCounterRecord {
    private long  disk_total;     /* total disk size in bytes */
    private long  disk_free;      /* total disk free in bytes */
    private float part_max_used;  /* utilization of most utilized partition */
    private long  reads;          /* reads issued */
    private long  bytes_read;     /* bytes read */
    private long  read_time;      /* read time (ms) */
    private long  writes;         /* writes completed */
    private long  bytes_written;  /* bytes written */
    private long  write_time;     /* write time (ms) */

    private HostDiskIoInfo(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }

    static public HostDiskIoInfo fromBytes(byte[] bytes, String host_ip, long timestamp)
            throws Exception {
        HostDiskIoInfo info = new HostDiskIoInfo(bytes, host_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        disk_total = buffer.getLong();
        disk_free = buffer.getLong();
        part_max_used = (float) buffer.getInt() / 100;
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
                "host_ip TEXT NOT NULL, " +
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
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip) );";
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        String sql = "INSERT INTO host_disk_io " +
                "(host_ip, timestamp, disk_total, disk_free, part_max_used, " +
                "reads, bytes_read, read_time, writes, bytes_written, write_time) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, host_ip);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, disk_total);
        pstmt.setLong(4, disk_free);
        pstmt.setFloat(5, part_max_used);
        pstmt.setLong(6, reads);
        pstmt.setLong(7, bytes_read);
        pstmt.setLong(8, read_time);
        pstmt.setLong(9, writes);
        pstmt.setLong(10, bytes_written);
        pstmt.setLong(11, write_time);
        pstmt.executeUpdate();
        Config.putJdbcConnection(conn);
    }
}
