package counterrecord;

import config.Config;

import java.util.HashMap;

public class VirtMemoryInfo extends VirtCounterRecord {
    private long vmem_memory;       /* memory in bytes used by domain */
    private long vmem_max_memory;   /* memory in bytes allowed */

    private VirtMemoryInfo(byte[] bytes, String source_ip, long timestamp) {
        super(bytes, source_ip, timestamp);
    }

    static public VirtMemoryInfo fromBytes(byte[] bytes, String source_ip, long timestamp)
            throws Exception {
        VirtMemoryInfo info = new VirtMemoryInfo(bytes, source_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vmem_memory = buffer.getLong();
        vmem_max_memory = buffer.getLong();
    }


    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vmem_memory", vmem_memory);
        map.put("vmem_max_memory", vmem_max_memory);
        return map;
    }
}
