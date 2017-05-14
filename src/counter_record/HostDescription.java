package counter_record;

import db.DB;
import log.LOG;
import java.sql.*;
import java.util.*;


public class HostDescription extends HostCounterRecord {
    private String hostname;     /* hostname, empty if unknown */
    private String uuid;         /* 16 bytes binary UUID, empty if unknown */
    private String machine_type; /* the processor family */
    private String os_name;      /* Operating system */
    private String os_release;   /* e.g. 2.6.9-42.ELsmp,xp-sp3, empty if unknown */

    static private HashMap<String, Long> host_map;
    static private HashMap<Long, String> id_to_host_map;
    static private Long row_id;

    private HostDescription(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }

    private HostDescription() {
        super();
    }

    static public HostDescription fromBytes(byte[] bytes, String host_ip, long timestamp)
            throws Exception {
        HostDescription info = new HostDescription(bytes, host_ip, timestamp);
        info.decode();
        return info;
    }

    public String getHostName() {
        return hostname;
    }

    @Override
    public void decode() throws Exception {
        hostname = Utils.getString(buffer, 64);
        //Config.LOG_INFO("hostname = %s", hostname);
        decodeUuid();

        decodeMachineType();

        decodeOsName();

        os_release = Utils.getString(buffer, 32);
    }

    private void decodeUuid() throws Exception {
        byte[] bytes = new byte[16];
        buffer.get(bytes);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            if (i < bytes.length - 1) {
                builder.append(String.format("%2x-", bytes[i]));
            } else {
                builder.append(String.format("%2x", bytes[i]));
            }
        }

        uuid = builder.toString();
    }

    private void decodeMachineType() throws Exception {
        int type = buffer.getInt();
        switch (type) {
            case 0:
                machine_type = "unknown";
                break;
            case 1:
                machine_type = "other";
                break;
            case 2:
                machine_type = "x86";
                break;
            case 3:
                machine_type = "x86_64";
                break;
            case 4:
                machine_type = "ia64";
                break;
            case 5:
                machine_type = "sparc";
                break;
            case 6:
                machine_type = "alpha";
                break;
            case 7:
                machine_type = "powerpc";
                break;
            case 8:
                machine_type = "m68k";
                break;
            case 9:
                machine_type = "mips";
                break;
            case 10:
                machine_type = "arm";
                break;
            case 11:
                machine_type = "hppa";
                break;
            case 12:
                machine_type = "s390";
                break;

            default:
                machine_type = "unknown";
                break;
        }
    }

    private void decodeOsName() throws Exception {
        int type = buffer.getInt();
        switch (type) {
            case 1:
                os_name = "other";
                break;
            case 2:
                os_name = "linux";
                break;
            case 3:
                os_name = "windows";
                break;
            case 4:
                os_name = "darwin";
                break;
            case 5:
                os_name = "hpux";
                break;
            case 6:
                os_name = "aix";
                break;
            case 7:
                os_name = "dragonfly";
                break;
            case 8:
                os_name = "freebsd";
                break;
            case 9:
                os_name = "netbsd";
                break;
            case 10:
                os_name = "openbsd";
                break;
            case 11:
                os_name = "osf";
                break;
            case 12:
                os_name = "solaris";
                break;
            default:
                os_name = "unknown";
                break;
        }
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("hostname", hostname);
        map.put("uuid", uuid);
        map.put("machine_type", machine_type);
        map.put("os_name", os_name);
        map.put("os_release", os_release);
        return map;
    }

    static public String schema() {
        //use rowid as host_id
        return "CREATE TABLE IF NOT EXISTS host_description (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT, " +
                "uuid TEXT, " +
                "machine_type TEXT, " +
                "os_name TEXT, " +
                "os_release TEXT );";
    }

    static public void loadFromDb(Connection conn) {
        //init
        row_id = 1L;
        host_map = new HashMap<String, Long>();
        id_to_host_map = new HashMap<Long, String>();

        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT rowid, host_ip FROM host_description";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Long rowid = rs.getLong("rowid");
                String host_ip = rs.getString("host_ip");
                host_map.put(host_ip, rowid);
                id_to_host_map.put(rowid, host_ip);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.ERROR("load host description error ", e.getMessage());
        }
    }

    static public boolean contains(String host) {
        synchronized (host_map) {
            return host_map.containsKey(host);
        }
    }

    static public Long getHostId(String host) {
        synchronized (host_map) {
            Long ret = host_map.get(host);
            return ret != null ? ret : -1;
        }
    }

    static public String getHostIp(Long host_id) {
        synchronized (id_to_host_map) {
            String ret = id_to_host_map.get(host_id);
            return ret != null ? ret : "unknown";
        }
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        if (HostDescription.contains(host_ip)) {

            Long id = getHostId(host_ip);

            String sql = "UPDATE host_description " +
                    "SET timestamp = ?, " +
                    "hostname = ?, " +
                    "uuid = ?, " +
                    "machine_type = ?, " +
                    "os_name = ?, " +
                    "os_release = ? " +
                    "WHERE rowid = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setLong(1, timestamp);
            pstmt.setString(2, hostname);
            pstmt.setString(3, uuid);
            pstmt.setString(4, machine_type);
            pstmt.setString(5, os_name);
            pstmt.setString(6, os_release);
            pstmt.setLong(7, id);
            pstmt.executeUpdate();

        } else {
            //do save
            String sql = "INSERT INTO host_description " +
                    "(host_ip, timestamp, hostname ,uuid, machine_type, os_name, os_release) " +
                    "VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, host_ip);
            pstmt.setLong(2, timestamp);
            pstmt.setString(3, hostname);
            pstmt.setString(4, uuid);
            pstmt.setString(5, machine_type);
            pstmt.setString(6, os_name);
            pstmt.setString(7, os_release);

            pstmt.executeUpdate();

            conn.commit();
            synchronized (host_ip) {
                host_map.put(host_ip, row_id);
                ++row_id;
            }
        }
    }

    static public void fromDb(List<HashMap> list) throws Exception {
        String sql = "SELECT host_ip, hostname, machine_type, os_name, os_release FROM host_description;";
        Statement stmt = DB.db_conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            HashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put("host_ip", rs.getString("host_ip"));
            map.put("hostname", rs.getString("hostname"));
            map.put("machine_type", rs.getString("machine_type"));
            map.put("os_name", rs.getString("os_name"));
            map.put("os_release", rs.getString("os_release"));
            list.add(map);
        }
    }

}