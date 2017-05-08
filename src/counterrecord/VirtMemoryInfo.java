package counterrecord;

import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class VirtMemoryInfo extends VirtCounterRecord {
    private long vmem_memory;       /* memory in bytes used by domain */
    private long vmem_max_memory;   /* memory in bytes allowed */

    private VirtMemoryInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public VirtMemoryInfo fromBytes(byte[] bytes, String source_ip, long timestamp)
            throws Exception {
        VirtMemoryInfo info = new VirtMemoryInfo(bytes, source_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vmem_memory = buffer.getLong();
        vmem_max_memory = buffer.getLong();
    }


    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vmem_memory", vmem_memory);
        map.put("vmem_max_memory", vmem_max_memory);
        return map;
    }

    public static String schema() {
        return "CREATE TABLE virt_memory (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT, " +
                "vmem_memory INTEGER, " +
                "vmem_max_memory INTEGER, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip) );";
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        String sql = "INSERT INTO virt_memory " +
                "(host_ip, timestamp, hostname, vmem_memory, vmem_max_memory)" +
                "VALUES(?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, host_ip);
        pstmt.setLong(2, timestamp);
        pstmt.setString(3, hostname);
        pstmt.setLong(4, vmem_memory);
        pstmt.setLong(5, vmem_max_memory);
        pstmt.executeUpdate();
        Config.putJdbcConnection(conn);
    }
}
