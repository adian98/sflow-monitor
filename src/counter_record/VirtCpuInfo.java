package counter_record;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class VirtCpuInfo extends VirtCounterRecord {
    private long vcpu_state;         /* virtDomainState */
    private long vcpu_cpu_time;      /* the CPU time used (ms) */
    private long vcpu_cpu_count;     /* number of virtual CPUs for the domain */

    private VirtCpuInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public VirtCpuInfo fromBytes(byte[] bytes, String sourceIP, long timestamp)
            throws Exception {
        VirtCpuInfo info = new VirtCpuInfo(bytes, sourceIP, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vcpu_state = Utils.bufferGetUint32(buffer);
        vcpu_cpu_time = Utils.bufferGetUint32(buffer);
        vcpu_cpu_count = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vcpu_state", vcpu_state);
        map.put("vcpu_cpu_time", vcpu_cpu_time);
        map.put("vcpu_cpu_count", vcpu_cpu_count);
        return map;
    }

    static public String schema() {
        return "CREATE TABLE IF NOT EXISTS virt_cpu (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "virt_id INTEGER, " +
                "vcpu_state INTEGER, " +
                "vcpu_cpu_time INTEGER, "  +
                "vcpu_cpu_count INTEGER, " +
                "FOREIGN KEY(host_id) REFERENCES host_description(rowid), " +
                "FOREIGN KEY(virt_id) REFERENCES virt_description(rowid));";
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "INSERT INTO virt_cpu " +
                "(host_id, timestamp, virt_id, vcpu_state, vcpu_cpu_time, vcpu_cpu_count) " +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, virt_id);
        pstmt.setLong(4, vcpu_state);
        pstmt.setLong(5, vcpu_cpu_time);
        pstmt.setLong(6, vcpu_cpu_count);
        pstmt.executeUpdate();
    }

    static public void fromDb(String hostname, Long timestamp, List<HashMap> list)
            throws Exception {
        Long start = timestamp - Utils.tenMinutes();

        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "SELECT * FROM virt_cpu WHERE virt_id = ? AND ? < timestamp AND timestamp <= ?;";

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
            map.put("vcpu_state", rs.getLong("vcpu_state"));
            map.put("vcpu_cpu_time", rs.getLong("vcpu_cpu_time"));
            map.put("vcpu_cpu_count", rs.getLong("vcpu_cpu_count"));
            list.add(map);
        }
    }
}
