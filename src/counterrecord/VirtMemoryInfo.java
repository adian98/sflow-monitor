package counterrecord;

import config.Config;

public class VirtMemoryInfo extends CounterRecord {
    private long vmem_memory;       /* memory in bytes used by domain */
    private long vmem_max_memory;   /* memory in bytes allowed */

    public VirtMemoryInfo(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void decode() throws Exception {
        vmem_memory = buffer.getLong();
        vmem_max_memory = buffer.getLong();
    }
}
