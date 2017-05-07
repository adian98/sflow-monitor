package counterrecord;
import config.Config;

import java.util.HashMap;

public class VirtNodeInfo extends VirtCounterRecord {
    private long vnode_mhz;          /* expected CPU frequency */
    private long vnode_cpus;         /* the number of active CPUs */
    private long vnode_memory;       /* memory size in bytes */
    private long vnode_memory_free;  /* unassigned memory in bytes */
    private long vnode_num_domains;  /* number of active domains */

    private VirtNodeInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public VirtNodeInfo fromBytes(byte[] bytes, String source_ip, long timestamp)
            throws Exception {
        VirtNodeInfo info = new VirtNodeInfo(bytes, source_ip, timestamp);
        info.decode();
        return info;
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
