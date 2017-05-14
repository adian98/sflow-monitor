package counter_record;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class VirtDiskIoInfo extends VirtCounterRecord {
    private long vdsk_capacity;     /* logical size in bytes */
    private long vdsk_allocation;   /* current allocation in bytes */
    private long vdsk_available;    /* remaining free bytes */
    private long vdsk_rd_req;       /* number of read requests */
    private long vdsk_rd_bytes;     /* number of read bytes */
    private long vdsk_wr_req;       /* number of write requests */
    private long vdsk_wr_bytes;     /* number of  written bytes */
    private long vdsk_errs;         /* read/write errors */

    private VirtDiskIoInfo(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }


    static public VirtDiskIoInfo fromBytes(byte[] bytes, String host_ip, long timestamp)
            throws Exception {
        VirtDiskIoInfo info = new VirtDiskIoInfo(bytes, host_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vdsk_capacity = buffer.getLong();
        vdsk_allocation = buffer.getLong();
        vdsk_available = buffer.getLong();
        vdsk_rd_req = Utils.bufferGetUint32(buffer);
        vdsk_rd_bytes = buffer.getLong();
        vdsk_wr_req = Utils.bufferGetUint32(buffer);
        vdsk_wr_bytes = buffer.getLong();
        vdsk_errs = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vdsk_capacity", vdsk_capacity);
        map.put("vdsk_allocation", vdsk_allocation);
        map.put("vdsk_available", vdsk_available);
        map.put("vdsk_rd_req", vdsk_rd_req);
        map.put("vdsk_rd_bytes", vdsk_rd_bytes);
        map.put("vdsk_wr_req", vdsk_wr_req);
        map.put("vdsk_wr_bytes", vdsk_wr_bytes);
        map.put("vdsk_errs", vdsk_errs);
        return map;
    }

    public static String schema() {
        return "CREATE TABLE IF NOT EXISTS virt_disk_io (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "virt_id INTEGER, " +
                "vdsk_capacity INTEGER, " +
                "vdsk_allocation INTEGER, " +
                "vdsk_available INTEGER, " +
                "vdsk_rd_req INTEGER, " +
                "vdsk_rd_bytes INTEGER, " +
                "vdsk_wr_req INTEGER, " +
                "vdsk_wr_bytes INTEGER, " +
                "vdsk_errs INTEGER, " +
                "FOREIGN KEY(host_id) REFERENCES host_description(rowid), " +
                "FOREIGN KEY(virt_id) REFERENCES virt_description(rowid));";
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);
        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "INSERT INTO virt_disk_io " +
                "(host_id, timestamp, virt_id, vdsk_capacity, vdsk_allocation, vdsk_available," +
                "vdsk_rd_req, vdsk_rd_bytes, vdsk_wr_req, vdsk_wr_bytes, vdsk_errs)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, host_id);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, virt_id);
        pstmt.setLong(4, vdsk_capacity);
        pstmt.setLong(5, vdsk_allocation);
        pstmt.setLong(6, vdsk_available);
        pstmt.setLong(7, vdsk_rd_req);
        pstmt.setLong(8, vdsk_rd_bytes);
        pstmt.setLong(9, vdsk_wr_req);
        pstmt.setLong(10, vdsk_wr_bytes);
        pstmt.setLong(11, vdsk_errs);
        pstmt.executeUpdate();
    }

    static public void fromDb(String hostname, Long timestamp, List<HashMap> list)
            throws Exception {
        Long start = timestamp - Utils.tenMinutes();

        Long virt_id = VirtDescription.getVirtId(hostname);

        String sql = "SELECT * FROM virt_disk_io WHERE virt_id = ? AND ? < timestamp AND timestamp <= ?;";

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
            map.put("vdsk_capacity", rs.getLong("vdsk_capacity"));
            map.put("vdsk_allocation", rs.getLong("vdsk_allocation"));
            map.put("vdsk_available", rs.getLong("vdsk_available"));
            map.put("vdsk_rd_req", rs.getLong("vdsk_rd_req"));
            map.put("vdsk_rd_bytes", rs.getLong("vdsk_rd_bytes"));
            map.put("vdsk_wr_req", rs.getLong("vdsk_wr_req"));
            map.put("vdsk_wr_bytes", rs.getLong("vdsk_wr_bytes"));
            map.put("vdsk_errs", rs.getLong("vdsk_errs"));
            list.add(map);
        }
    }

}
