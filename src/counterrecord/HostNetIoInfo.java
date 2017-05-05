package counterrecord;

import config.Config;

public class HostNetIoInfo extends CounterRecord {
    private long bytes_in;     /* total bytes in */
    private long packets_in;   /* total packets in */
    private long errs_in;      /* total errors in */
    private long drops_in;     /* total drops in */
    private long bytes_out;    /* total bytes out */
    private long packets_out;  /* total packets out */
    private long errs_out;     /* total errors out */
    private long drops_out;    /* total drops out */

    public HostNetIoInfo(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void decode() throws Exception {
        bytes_in = buffer.getLong();
        packets_in = Utils.bufferGetUint32(buffer);
        errs_in = Utils.bufferGetUint32(buffer);
        drops_in = Utils.bufferGetUint32(buffer);

        bytes_out = buffer.getLong();
        packets_out = Utils.bufferGetUint32(buffer);;
        errs_out = Utils.bufferGetUint32(buffer);
        drops_out = Utils.bufferGetUint32(buffer);

    }
}
