package counterrecord;
import config.Config;

import java.util.HashMap;

public class VirtNodeInfo extends CounterRecord {
    private long vnode_mhz;          /* expected CPU frequency */
    private long vnode_cpus;         /* the number of active CPUs */
    private long vnode_memory;       /* memory size in bytes */
    private long vnode_memory_free;  /* unassigned memory in bytes */
    private long vnode_num_domains;  /* number of active domains */

    public VirtNodeInfo(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }

    @Override
    public void decode() throws Exception {
        vnode_mhz = Utils.bufferGetUint32(buffer);
        vnode_cpus = Utils.bufferGetUint32(buffer);
        vnode_memory = buffer.getLong();
        vnode_memory_free = buffer.getLong();
        vnode_num_domains = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vnode_mhz", vnode_mhz);
        map.put("vnode_cpus", vnode_cpus);
        map.put("vnode_memory", vnode_memory);
        map.put("vnode_memory_free", vnode_memory_free);
        map.put("vnode_num_domains", vnode_num_domains);
        return map;
    }
}
