package counterrecord;

import config.Config;

/**
 * Created by cloud on 17-5-3.
 */
public class VirtNetIoInfo extends CounterRecord {

    private long vnio_bytes_in;    /* total bytes received */
    private long vnio_packets_in;  /* total packets received */
    private long vnio_errs_in;     /* total receive errors */
    private long vnio_drops_in;    /* total receive drops */
    private long vnio_bytes_out;   /* total bytes transmitted */
    private long vnio_packets_out; /* total packets transmitted */
    private long vnio_errs_out;    /* total transmit errors */
    private long vnio_drops_out;   /* total transmit drops */

    public VirtNetIoInfo(byte[] bytes) {
        super(bytes);
    }


    @Override
    public void decode() throws Exception {

        vnio_bytes_in = buffer.getLong();
        vnio_packets_in  = Utils.bufferGetUint32(buffer);
        vnio_errs_in = Utils.bufferGetUint32(buffer);
        vnio_drops_in = Utils.bufferGetUint32(buffer);
        vnio_bytes_out = buffer.getLong();
        vnio_packets_out = Utils.bufferGetUint32(buffer);
        vnio_errs_out = Utils.bufferGetUint32(buffer);
        vnio_drops_out = Utils.bufferGetUint32(buffer);
    }
}
