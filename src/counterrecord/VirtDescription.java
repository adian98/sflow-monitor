package counterrecord;

import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class VirtDescription extends VirtCounterRecord {
    static private HashSet<String> virt_list;

    public VirtDescription(String host_ip, long timestamp, String host_name) {
        super(null, host_ip, timestamp);
        setHostName(host_name);
    }

    public static String schema() {
        return "CREATE TABLE virt_description (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT);";
    }

    static public void loadFromDb() {
        virt_list = new HashSet<String>();
        Connection conn;
        try {
            conn = Config.getJdbcConnection();
            String sql = "SELECT hostname FROM virt_description";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                virt_list.add(rs.getString("virt_description"));
            }

            Config.putJdbcConnection(conn);
        } catch (Exception e) {
            Config.LOG_ERROR(e.getMessage());
        }
    }


    static public synchronized boolean contains(String host_name) {
        return virt_list.contains(host_name);
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        if (HostDescription.contains(host_name)) {
            //update
            String sql = "UPDATE virt_description " +
                    "set host_ip = ?, " +
                    "SET timestamp = ?, " +
                    "WHERE hostname = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, host_ip);
            pstmt.setLong(2, timestamp);
            pstmt.setString(3, host_name);
            pstmt.executeUpdate();

        } else {
            //do save
            String sql = "INSERT INTO virt_description " +
                    "(host_ip, timestamp, hostname) " +
                    "VALUES(?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, host_ip);
            pstmt.setLong(2, timestamp);
            pstmt.setString(3, host_name);

            pstmt.executeUpdate();
            synchronized (virt_list) {
                virt_list.add(host_name);
            }
        }
        Config.putJdbcConnection(conn);
    }
}
