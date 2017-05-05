package counterrecord;

import config.Config;

public class HostMemoryInfo extends CounterRecord {
    private long mem_total;   /* total bytes */
    private long mem_free;    /* free bytes */
    private long mem_shared;  /* shared bytes */
    private long mem_buffers; /* buffers bytes */
    private long mem_cached;  /* cached bytes */
    private long swap_total;  /* swap total bytes */
    private long swap_free;   /* swap free bytes */
    private long page_in;     /* page in count */
    private long page_out;    /* page out count */
    private long swap_in;     /* swap in count */
    private long swap_out;    /* swap out count */

    public HostMemoryInfo(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void decode() throws Exception {
        mem_total = buffer.getLong();
        mem_free = buffer.getLong();
        mem_shared = buffer.getLong();
        mem_buffers = buffer.getLong();
        mem_cached = buffer.getLong();
        swap_total = buffer.getLong();
        swap_free = buffer.getLong();

        page_in = Utils.bufferGetUint32(buffer);
        page_out = Utils.bufferGetUint32(buffer);
        swap_in = Utils.bufferGetUint32(buffer);
        swap_out = Utils.bufferGetUint32(buffer);
    }
}
