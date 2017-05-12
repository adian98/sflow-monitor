package counter_record;

import config.Config;

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
        return "CREATE TABLE virt_cpu (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT, " +
                "vcpu_state INTEGER, " +
                "vcpu_cpu_time INTEGER, "  +
                "vcpu_cpu_count INTEGER, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip));";
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        String sql = "INSERT INTO virt_cpu " +
                "(host_ip, timestamp, hostname, vcpu_state, vcpu_cpu_time, vcpu_cpu_count) " +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, host_ip);
        pstmt.setLong(2, timestamp);
        pstmt.setString(3, hostname);
        pstmt.setLong(4, vcpu_state);
        pstmt.setLong(5, vcpu_cpu_time);
        pstmt.setLong(6, vcpu_cpu_count);
        pstmt.executeUpdate();
        Config.putJdbcConnection(conn);
    }

    static public List<HashMap> fromDb(String hostname, Long timestamp) throws Exception {
        Long start = timestamp - Utils.tenMinutes();

        List<HashMap> list = new ArrayList<HashMap>();
        Connection conn = Config.getJdbcConnection();

        String sql = "SELECT * FROM virt_cpu WHERE hostname = ? AND ? < timestamp AND timestamp <= ?;";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, hostname);
        pstmt.setLong(2, start);
        pstmt.setLong(3, timestamp);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            HashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("host_ip", rs.getString("host_ip"));
            map.put("timestamp", rs.getLong("timestamp"));
            map.put("hostname", rs.getString("hostname"));
            map.put("vcpu_state", rs.getLong("vcpu_state"));
            map.put("vcpu_cpu_time", rs.getLong("vcpu_cpu_time"));
            map.put("vcpu_cpu_count", rs.getLong("vcpu_cpu_count"));
            list.add(map);
        }
        Config.putJdbcConnection(conn);
        return list;
    }
}
