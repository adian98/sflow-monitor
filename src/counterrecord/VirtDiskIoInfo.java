package counterrecord;
import config.Config;

import java.util.HashMap;


public class VirtDiskIoInfo extends VirtCounterRecord {
    private long vdsk_capacity;     /* logical size in bytes */
    private long vdsk_allocation;   /* current allocation in bytes */
    private long vdsk_available;    /* remaining free bytes */
    private long vdsk_rd_req;       /* number of read requests */
    private long vdsk_rd_bytes;     /* number of read bytes */
    private long vdsk_wr_req;       /* number of write requests */
    private long vdsk_wr_bytes;     /* number of  written bytes */
    private long vdsk_errs;         /* read/write errors */

    private VirtDiskIoInfo(byte[] bytes, String host_ip, long timestamp) {
        super(bytes, host_ip, timestamp);
    }


    static public VirtDiskIoInfo fromBytes(byte[] bytes, String host_ip, long timestamp)
            throws Exception {
        VirtDiskIoInfo info = new VirtDiskIoInfo(bytes, host_ip, timestamp);
        info.decode();
        return info;
    }

    @Override
    public void decode() throws Exception {
        vdsk_capacity = buffer.getLong();
        vdsk_allocation = buffer.getLong();
        vdsk_available = buffer.getLong();
        vdsk_rd_req = Utils.bufferGetUint32(buffer);
        vdsk_rd_bytes = buffer.getLong();
        vdsk_wr_req = Utils.bufferGetUint32(buffer);
        vdsk_wr_bytes = buffer.getLong();
        vdsk_errs = Utils.bufferGetUint32(buffer);
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("vdsk_capacity", vdsk_capacity);
        map.put("vdsk_allocation", vdsk_allocation);
        map.put("vdsk_available", vdsk_available);
        map.put("vdsk_rd_req", vdsk_rd_req);
        map.put("vdsk_rd_bytes", vdsk_rd_bytes);
        map.put("vdsk_wr_req", vdsk_wr_req);
        map.put("vdsk_wr_bytes", vdsk_wr_bytes);
        map.put("vdsk_errs", vdsk_errs);
        return map;
    }
}
