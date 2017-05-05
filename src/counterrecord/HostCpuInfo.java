package counterrecord;

import config.Config;

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

    public HostCpuInfo(byte[] bytes) {
        super(bytes);
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
}
