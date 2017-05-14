package counter_record;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class VirtNetIoInfo extends VirtCounterRecord {

    private long vnio_bytes_in;    /* total bytes received */
    private long vnio_packets_in;  /* total packets received */
    private long vnio_errs_in;     /* total receive errors */
    private long vnio_drops_in;    /* total receive drops */
    private long vnio_bytes_out;   /* total bytes transmitted */
    private long vnio_packets_out; /* total packets transmitted */
    private long vnio_errs_out;    /* total transmit errors */
    private long vnio_drops_out;   /* total transmit drops */

    private VirtNetIoInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public VirtNetIoInfo fromBytes(byte[] bytes, String source_ip, long timestamp)
            throws Exception {
        VirtNetIoInfo info = new VirtNetIoInfo(bytes, source_ip, timestamp);
        info.decode();
        return info;
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

    public static String schema() {
        return "CREATE TABLE IF NOT EXISTS virt_net_io (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "virt_id INTEGER, " +
                "vnio_bytes_in INTEGER, " +
                "vnio_packets_in INTEGER, " +
                "vnio_errs_in INTEGER, " +
                "vnio_drops_in INTEGER, " +
                "vnio_bytes_out INTEGER, " +
                "vnio_packets_out INTEGER, " +
                "vnio_errs_out INTEGER, " +
                "vnio_drops_out INTEGER, " +
                "FOREIGN KEY(host_id) REFERENCES host_description(rowid), " +
                "FOREIGN KEY(virt_id) REFERENCES virt_description(rowid));";
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "INSERT INTO virt_net_io " +
                "(host_id, timestamp, virt_id, vnio_bytes_in, vnio_packets_in, vnio_errs_in, vnio_drops_in, " +
                "vnio_bytes_out, vnio_packets_out, vnio_errs_out, vnio_drops_out)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, virt_id);
        pstmt.setLong(4, vnio_bytes_in);
        pstmt.setLong(5, vnio_packets_in);
        pstmt.setLong(6, vnio_errs_in);
        pstmt.setLong(7, vnio_drops_in);
        pstmt.setLong(8, vnio_bytes_out);
        pstmt.setLong(9, vnio_packets_out);
        pstmt.setLong(10, vnio_errs_out);
        pstmt.setLong(11, vnio_drops_out);
        pstmt.executeUpdate();
    }

    static public void fromDb(String hostname, Long timestamp, List<HashMap> list)
            throws Exception {
        Long start = timestamp - Utils.tenMinutes();
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "SELECT * FROM virt_net_io WHERE virt_id = ? AND ? < timestamp AND timestamp <= ?;";

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
            map.put("vnio_bytes_in", rs.getLong("vnio_bytes_in"));
            map.put("vnio_packets_in", rs.getLong("vnio_packets_in"));
            map.put("vnio_errs_in", rs.getLong("vnio_errs_in"));
            map.put("vnio_drops_in", rs.getLong("vnio_drops_in"));
            map.put("vnio_bytes_out", rs.getLong("vnio_bytes_out"));
            map.put("vnio_packets_out", rs.getLong("vnio_packets_out"));
            map.put("vnio_errs_out", rs.getLong("vnio_errs_out"));
            map.put("vnio_drops_out", rs.getLong("vnio_drops_out"));
            list.add(map);
        }
    }
}
