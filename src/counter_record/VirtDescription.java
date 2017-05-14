package counter_record;

import db.DB;
import log.LOG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class VirtDescription extends VirtCounterRecord {
    static private HashMap<String, Long> virt_map;
    static private Long row_id;

    public VirtDescription(String host_ip, long timestamp, String host_name) {
        //fix me : remove new byte[1]
        super(new byte[1], host_ip, timestamp);
        setHostName(host_name);
    }

    public static String schema() {
        //use rowid as virt_id
        return "CREATE TABLE IF NOT EXISTS virt_description (" +
                "host_id INTEGER, " +
                "timestamp INTEGER, " +
                "hostname TEXT) ;";
    }

    static public void loadFromDb(Connection conn) {
        //init
        virt_map = new HashMap<String, Long>();
        row_id = 1L;

        try {
            String sql = "SELECT rowid, hostname FROM virt_description";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Long id = rs.getLong("rowid");
                String hostname = rs.getString("hostname");
                virt_map.put(hostname, id);
            }
        } catch (Exception e) {
            LOG.ERROR(e.getMessage());
        }
    }


    static public boolean contains(String host_name) {
        synchronized (virt_map) {
            return virt_map.containsKey(host_name);
        }
    }

    static public Long getVirtId(String host_name) {
        synchronized (virt_map) {
            Long ret = virt_map.get(host_name);
            return ret != null ? ret : -1;
        }
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        Long host_id = HostDescription.getHostId(host_ip);

        if (VirtDescription.contains(hostname)) {
            //update
            Long virt_id = VirtDescription.getVirtId(hostname);
            String sql = "UPDATE virt_description " +
                    "set host_id = ?, " +
                    "timestamp = ? " +
                    "WHERE rowid = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, host_id);
            pstmt.setLong(2, timestamp);
            pstmt.setLong(3, virt_id);
            pstmt.executeUpdate();

        } else {
            //do save
            String sql = "INSERT INTO virt_description " +
                    "(host_id, timestamp, rowid) " +
                    "VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, host_id);
            pstmt.setLong(2, timestamp);
            pstmt.setLong(3, row_id);

            pstmt.executeUpdate();

            conn.commit();
            synchronized (virt_map) {
                virt_map.put(hostname, row_id);
                ++row_id;
            }
        }
    }

    static public void fromDb(List<HashMap> list)
            throws Exception {

        String sql = "SELECT host_id, rowid FROM virt_description;";
        Statement stmt = DB.db_conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            HashMap<String, String> map = new HashMap<String, String>();
            Long host_id = rs.getLong("host_id");
            Long virt_id = rs.getLong("rowid");

            map.put("host_ip", HostDescription.getHostIp(host_id));
            map.put("hostname", rs.getString("hostname"));
            list.add(map);
        }
    }
}
