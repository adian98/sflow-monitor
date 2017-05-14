package counter_record;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
        return "CREATE TABLE IF NOT EXISTS virt_memory (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "virt_id INTEGER, " +
                "vmem_memory INTEGER, " +
                "vmem_max_memory INTEGER, " +
                "FOREIGN KEY(host_id) REFERENCES host_description(rowid), " +
                "FOREIGN KEY(virt_id) REFERENCES virt_description(rowid));";
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "INSERT INTO virt_memory " +
                "(host_id, timestamp, virt_id, vmem_memory, vmem_max_memory)" +
                "VALUES(?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, virt_id);
        pstmt.setLong(4, vmem_memory);
        pstmt.setLong(5, vmem_max_memory);
        pstmt.executeUpdate();
    }

    static public void fromDb(String hostname, Long timestamp, List<HashMap> list)
            throws Exception {
        Long start = timestamp - Utils.tenMinutes();
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "SELECT * FROM virt_memory WHERE virt_id = ? AND ? < timestamp AND timestamp <= ?;";

        PreparedStatement pstmt = DB.db_conn.prepareStatement(sql);
        pstmt.setLong(1, virt_id);
        pstmt.setLong(2, start);
        pstmt.setLong(3, timestamp);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            HashMap<String, Object> map = new LinkedHashMap<String, Object>();
            Long host_id = rs.getLong("host_id");
            map.put("host_ip", HostDescription.getHostIp(host_id));
            map.put("timestamp", rs.getLong("timestamp"));
            map.put("hostname", hostname);
            map.put("vmem_memory", rs.getLong("vmem_memory"));
            map.put("vmem_max_memory", rs.getLong("vmem_max_memory"));
            list.add(map);
        }
    }
}
