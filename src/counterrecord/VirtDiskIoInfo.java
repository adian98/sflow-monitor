package counterrecord;
import config.Config;


public class VirtDiskIoInfo extends CounterRecord {
    private long vdsk_capacity;     /* logical size in bytes */
    private long vdsk_allocation;   /* current allocation in bytes */
    private long vdsk_available;    /* remaining free bytes */
    private long vdsk_rd_req;       /* number of read requests */
    private long vdsk_rd_bytes;     /* number of read bytes */
    private long vdsk_wr_req;       /* number of write requests */
    private long vdsk_wr_bytes;     /* number of  written bytes */
    private long vdsk_errs;         /* read/write errors */

    public VirtDiskIoInfo(byte[] bytes) {
        super(bytes);
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
}
