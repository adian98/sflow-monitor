package counterrecord;

import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class HostCpuInfo extends HostCounterRecord {

    private float cpu_load_one;       /* 1 minute load avg., -1.0 = unknown */
    private float cpu_load_five;      /* 5 minute load avg., -1.0 = unknown */
    private float cpu_load_fifteen;   /* 15 minute load avg., -1.0 = unknown */
    private long cpu_proc_run;        /* total number of running processes */
    private long cpu_proc_total;      /* total number of processes */
    private long cpu_num;             /* number of CPUs */
    private long cpu_speed;           /* speed in MHz of CPU */
    private long cpu_uptime;          /* seconds since last reboot */
    private long cpu_user;            /* user time (ms) */
    private long cpu_nice;            /* nice time (ms) */
    private long cpu_system;          /* system time (ms) */
    private long cpu_idle;            /* idle time (ms) */
    private long cpu_waiting_io;      /* time waiting for I/O to complete (ms) */
    private long cpu_intr;            /* time servicing interrupts (ms) */
    private long cpu_sintr;           /* time servicing soft interrupts (ms) */
    private long cpu_interrupts;      /* interrupt count */
    private long cpu_contexts;        /* context switch count */

    private HostCpuInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    static public HostCpuInfo fromBytes(byte[] bytes, String sourceIP, long timestamp)
            throws Exception {
        HostCpuInfo info = new HostCpuInfo(bytes, sourceIP, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        cpu_load_one = buffer.getFloat();
        cpu_load_five = buffer.getFloat();
        cpu_load_fifteen = buffer.getFloat();
        cpu_proc_run = Utils.bufferGetUint32(buffer);
        cpu_proc_total = Utils.bufferGetUint32(buffer);
        cpu_num = Utils.bufferGetUint32(buffer);
        cpu_speed = Utils.bufferGetUint32(buffer);
        cpu_uptime = Utils.bufferGetUint32(buffer);
        cpu_user = Utils.bufferGetUint32(buffer);
        cpu_nice = Utils.bufferGetUint32(buffer);
        cpu_system = Utils.bufferGetUint32(buffer);
        cpu_idle = Utils.bufferGetUint32(buffer);
        cpu_waiting_io = Utils.bufferGetUint32(buffer);
        cpu_intr = Utils.bufferGetUint32(buffer);
        cpu_sintr = Utils.bufferGetUint32(buffer);
        cpu_interrupts = Utils.bufferGetUint32(buffer);
        cpu_contexts = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("cpu_load_one", cpu_load_one);
        map.put("cpu_load_five", cpu_load_five);
        map.put("cpu_load_fifteen", cpu_load_fifteen);
        map.put("cpu_proc_run", cpu_proc_run);
        map.put("cpu_proc_total", cpu_proc_total);
        map.put("cpu_num", cpu_num);
        map.put("cpu_speed", cpu_speed);
        map.put("cpu_uptime", cpu_uptime);
        map.put("cpu_user", cpu_user);
        map.put("cpu_nice", cpu_nice);
        map.put("cpu_system", cpu_system);
        map.put("cpu_idle", cpu_idle);
        map.put("cpu_waiting_io", cpu_waiting_io);
        map.put("cpu_intr", cpu_intr);
        map.put("cpu_sintr", cpu_sintr);
        map.put("cpu_interrupts", cpu_interrupts);
        map.put("cpu_contexts", cpu_contexts);

        return map;
    }

    static public String schema() {
        return "CREATE TABLE host_cpu (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "cpu_load_one REAL, " +
                "cpu_load_five REAL, " +
                "cpu_load_fifteen REAL, " +
                "cpu_proc_run INTEGER, " +
                "cpu_proc_total INTEGER, " +
                "cpu_num INTEGER, " +
                "cpu_speed INTEGER, " +
                "cpu_uptime INTEGER, " +
                "cpu_user INTEGER, " +
                "cpu_nice INTEGER, " +
                "cpu_system INTEGER, " +
                "cpu_idle INTEGER, " +
                "cpu_waiting_io INTEGER, " +
                "cpu_intr INTEGER, " +
                "cpu_sintr INTEGER, " +
                "cpu_interrupts INTEGER, " +
                "cpu_contexts INTEGER, " +
                "FOREIGN KEY(host_ip) REFERENCES host_description(host_ip) );";
    }

    @Override
    public void saveToDb() throws Exception {
        Connection conn = Config.getJdbcConnection();

        String sql = "INSERT INTO host_cpu " +
                "(host_ip, timestamp, cpu_load_one, cpu_load_five, cpu_load_fifteen, cpu_proc_run, " +
                "cpu_proc_total, cpu_num, cpu_speed, cpu_uptime, cpu_user, cpu_nice, cpu_system, cpu_idle, " +
                "cpu_waiting_io, cpu_intr, cpu_sintr, cpu_interrupts, cpu_contexts) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, host_ip);
        pstmt.setLong(2, timestamp);
        pstmt.setFloat(3, cpu_load_one);
        pstmt.setFloat(4, cpu_load_five);
        pstmt.setFloat(5, cpu_load_fifteen);
        pstmt.setLong(6, cpu_proc_run);
        pstmt.setLong(7, cpu_proc_total);
        pstmt.setLong(8, cpu_num);
        pstmt.setLong(9, cpu_speed);
        pstmt.setLong(10, cpu_uptime);
        pstmt.setLong(11, cpu_user);
        pstmt.setLong(12, cpu_nice);
        pstmt.setLong(13, cpu_system);
        pstmt.setLong(14, cpu_idle);
        pstmt.setLong(15, cpu_waiting_io);
        pstmt.setLong(16, cpu_intr);
        pstmt.setLong(17, cpu_sintr);
        pstmt.setLong(18, cpu_interrupts);
        pstmt.setLong(19, cpu_contexts);
        pstmt.executeUpdate();
        Config.putJdbcConnection(conn);
    }
}
