package counterrecord;
import java.nio.ByteBuffer;


public class CounterRecord {
    protected ByteBuffer buffer;
    protected String source_ip;
    protected long timestamp;

    public CounterRecord(byte[] bytes, String source_ip, long timestamp) {
        this.source_ip = source_ip;
        this.timestamp = timestamp;
        buffer = ByteBuffer.wrap(bytes);
    }

    public void decode() throws Exception {
        throw new Exception("not implement");
    }




}
