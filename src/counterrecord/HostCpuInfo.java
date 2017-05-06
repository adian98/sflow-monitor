package counterrecord;

import config.Config;

import java.util.HashMap;

public class HostCpuInfo extends CounterRecord {

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



    public HostCpuInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
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

}
