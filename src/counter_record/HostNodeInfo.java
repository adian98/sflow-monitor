package counter_record;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HostNodeInfo extends HostCounterRecord {
    private long vnode_mhz;          /* expected CPU frequency */
    private long vnode_cpus;         /* the number of active CPUs */
    private long vnode_memory;       /* memory size in bytes */
    private long vnode_memory_free;  /* unassigned memory in bytes */
    private long vnode_num_domains;  /* number of active domains */

    private HostNodeInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public HostNodeInfo fromBytes(byte[] bytes, String source_ip, long timestamp)
            throws Exception {
        HostNodeInfo info = new HostNodeInfo(bytes, source_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vnode_mhz = Utils.bufferGetUint32(buffer);
        vnode_cpus = Utils.bufferGetUint32(buffer);
        vnode_memory = buffer.getLong();
        vnode_memory_free = buffer.getLong();
        vnode_num_domains = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vnode_mhz", vnode_mhz);
        map.put("vnode_cpus", vnode_cpus);
        map.put("vnode_memory", vnode_memory);
        map.put("vnode_memory_free", vnode_memory_free);
        map.put("vnode_num_domains", vnode_num_domains);
        return map;
    }

    public static String schema() {
        return "CREATE TABLE IF NOT EXISTS host_node (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "vnode_mhz INTEGER, " +
                "vnode_cpus INTEGER, " +
                "vnode_memory INTEGER, " +
                "vnode_memory_free INTEGER, " +
                "vnode_num_domains INTEGER, " +
                "FOREIGN KEY(host_id) REFERENCES host_description(rowid) );";
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);
        String sql = "INSERT INTO host_node " +
                "(host_id, timestamp, vnode_mhz, vnode_cpus, vnode_memory, vnode_memory_free, vnode_num_domains)" +
                "VALUES(?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3,  vnode_mhz);
        pstmt.setLong(4, vnode_cpus);
        pstmt.setLong(5, vnode_memory);
        pstmt.setLong(6, vnode_memory_free);
        pstmt.setLong(7, vnode_num_domains);
        pstmt.executeUpdate();
    }

    static public void fromDb(String host_ip, Long timestamp, List<HashMap> list) throws Exception {
        Long start = timestamp - Utils.tenMinutes();

        Long host_id = HostDescription.getHostId(host_ip);
        String sql = "SELECT * FROM host_node WHERE host_id = ? AND ? < timestamp AND timestamp <= ?;";

        PreparedStatement pstmt = DB.db_conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, start);
        pstmt.setLong(3, timestamp);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            HashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("host_ip", host_ip);
            map.put("timestamp", rs.getLong("timestamp"));
            map.put("vnode_mhz", rs.getLong("vnode_mhz"));
            map.put("vnode_cpus", rs.getLong("vnode_cpus"));
            map.put("vnode_memory", rs.getLong("vnode_memory"));
            map.put("vnode_memory_free", rs.getLong("vnode_memory_free"));
            map.put("vnode_num_domains", rs.getLong("vnode_num_domains"));
            list.add(map);
        }
    }
}
