package counterrecord;

import config.Config;

import java.util.HashMap;

public class VirtCpuInfo extends CounterRecord {
    private long vcpu_state;         /* virtDomainState */
    private long vcpu_cpu_time;      /* the CPU time used (ms) */
    private long vcpu_cpu_count;     /* number of virtual CPUs for the domain */

    public VirtCpuInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    @Override
    public void decode() throws Exception {
        vcpu_state = Utils.bufferGetUint32(buffer);
        vcpu_cpu_time = Utils.bufferGetUint32(buffer);
        vcpu_cpu_count = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vcpu_state", vcpu_state);
        map.put("vcpu_cpu_time", vcpu_cpu_time);
        map.put("vcpu_cpu_count", vcpu_cpu_count);
        return map;
    }
}
