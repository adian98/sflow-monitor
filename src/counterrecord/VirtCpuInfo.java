package counterrecord;

import config.Config;

public class VirtCpuInfo extends CounterRecord {
    private long vcpu_state;         /* virtDomainState */
    private long vcpu_cpu_time;      /* the CPU time used (ms) */
    private long vcpu_cpu_count;     /* number of virtual CPUs for the domain */

    public VirtCpuInfo(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void decode() throws Exception {
        vcpu_state = Utils.bufferGetUint32(buffer);
        vcpu_cpu_time = Utils.bufferGetUint32(buffer);
        vcpu_cpu_count = Utils.bufferGetUint32(buffer);
    }



}
