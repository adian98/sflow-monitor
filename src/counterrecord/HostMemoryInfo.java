package counterrecord;

import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class HostMemoryInfo extends HostCounterRecord {
    private long mem_total;   /* total bytes */
    private long mem_free;    /* free bytes */
    private long mem_shared;  /* shared bytes */
    private long mem_buffers; /* buffers bytes */
    private long mem_cached;  /* cached bytes */
    private long swap_total;  /* swap total bytes */
    private long swap_free;   /* swap free bytes */
    private long page_in;     /* page in count */
    private long page_out;    /* page out count */
    private long swap_in;     /* swap in count */
    private long swap_out;    /* swap out count */

    private HostMemoryInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    static public HostMemoryInfo fromBytes(byte[] bytes, String sourceIP, long timestamp)
            throws Exception {
        HostMemoryInfo info = new HostMemoryInfo(bytes, sourceIP, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        mem_total = buffer.getLong();
        mem_free = buffer.getLong();
        mem_shared = buffer.getLong();
        mem_buffers = buffer.getLong();
        mem_cached = buffer.getLong();
        swap_total = buffer.getLong();
        swap_free = buffer.getLong();

        page_in = Utils.bufferGetUint32(buffer);
        page_out = Utils.bufferGetUint32(buffer);
        swap_in = Utils.bufferGetUint32(buffer);
        swap_out = Utils.bufferGetUint32(buffer);
    }


    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("mem_total", mem_total);
        map.put("mem_free", mem_free);
        map.put("mem_shared", mem_shared);
        map.put("mem_buffers", mem_buffers);
        map.put("mem_cached",mem_cached);
        map.put("swap_total", swap_total);
        map.put("swap_free", swap_free);
        map.put("page_in", page_in);
        map.put("page_out", page_out);
        map.put("swap_in", swap_in);
        map.put("swap_out", swap_out);
        return map;
    }

    static public String schema() {
        return "CREATE TABLE host_memory (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "mem_total INTEGER, " +
                "mem_free INTEGER, " +
                "mem_shared INTEGER, " +
                "mem_buffers INTEGER, " +
                "mem_cached  INTEGER, " +
                "swap_total INTEGER, " +
                "swap_free INTEGER, " +
                "page_in INTEGER, " +
                "page_out INTEGER, " +
                "swap_in INTEGER, " +
                "swap_out INTEGER, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip) );";
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        String sql = "INSERT INTO host_memory " +
                "(host_ip, timestamp, mem_total, mem_free, mem_shared, mem_buffers, " +
                "mem_cached, swap_total, swap_free, page_in, page_out, swap_in, swap_out) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, host_ip);
        pstmt.setLong(2, timestamp);
        pstmt.setLong(3, mem_total);
        pstmt.setLong(4, mem_free);
        pstmt.setLong(5, mem_shared);
        pstmt.setLong(6, mem_buffers);
        pstmt.setLong(7, mem_cached);
        pstmt.setLong(8, swap_total);
        pstmt.setLong(9, swap_free);
        pstmt.setLong(10, page_in);
        pstmt.setLong(11, page_out);
        pstmt.setLong(12, swap_in);
        pstmt.setLong(13, swap_out);
        pstmt.executeUpdate();
        Config.putJdbcConnection(conn);
    }
}
