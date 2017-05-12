package counter_record;

import db.DB;
import log.LOG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class VirtDescription extends VirtCounterRecord {
    static private HashSet<String> virt_list;

    public VirtDescription(String host_ip, long timestamp, String host_name) {
        //fix me : remove new byte[1]
        super(new byte[1], host_ip, timestamp);
        setHostName(host_name);
    }

    public static String schema() {
        return "CREATE TABLE virt_description (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip));";
    }

    static public void loadFromDb(Connection conn) {
        virt_list = new HashSet<String>();
        try {
            String sql = "SELECT hostname FROM virt_description";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                virt_list.add(rs.getString("hostname"));
            }
        } catch (Exception e) {
            LOG.ERROR(e.getMessage());
        }
    }


    static public synchronized boolean contains(String host_name) {
        return virt_list.contains(host_name);
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        if (VirtDescription.contains(hostname)) {
            //update
            String sql = "UPDATE virt_description " +
                    "set host_ip = ?, " +
                    "timestamp = ? " +
                    "WHERE hostname = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, host_ip);
            pstmt.setLong(2, timestamp);
            pstmt.setString(3, hostname);
            pstmt.executeUpdate();

        } else {
            //do save
            String sql = "INSERT INTO virt_description " +
                    "(host_ip, timestamp, hostname) " +
                    "VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, host_ip);
            pstmt.setLong(2, timestamp);
            pstmt.setString(3, hostname);

            pstmt.executeUpdate();

            conn.commit();
            synchronized (virt_list) {
                virt_list.add(hostname);
            }
        }
    }

    static public void fromDb(List<HashMap> list)
            throws Exception {
        String sql = "SELECT host_ip, hostname FROM virt_description;";
        Statement stmt = DB.db_conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("host_ip", rs.getString("host_ip"));
            map.put("hostname", rs.getString("hostname"));
            list.add(map);
        }
    }
}
