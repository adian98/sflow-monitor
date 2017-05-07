import config.Config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;



public class SFlowDatagram {
    static private final int FLOW_SAMPLE_TYPE = 0x0 | 0x1; //enterprise = 0, format = 1 Flow Sample
    static private final int COUNTERS_SAMPLE_TYPE = 0x0 | 0x2; //enterprise = 0, format = 2 Counter Sample
    static private final int FLOW_SAMPLE_EXPANDED_TYPE = 0x0 | 0x3; //enterprise = 0, format = 3 Expanded Flow Sample
    static private final int COUNTERS_SAMPLE_EXPANDED_TYPE = 0x0 | 0x4; //enterprise = 0, format = 4 Expanded Counter Sample

    private boolean is_v4;
    private ByteBuffer buffer;
    private int size;
    private String source_ip;
    private long timestamp;
    private int version;
    private String agent_address;
    private int samples;


    public SFlowDatagram(ByteBuffer buffer, InetAddress socketAddress) {
        is_v4 = socketAddress instanceof Inet4Address;
        this.buffer = buffer;
        size = buffer.remaining();
        source_ip = socketAddress.getHostAddress();
        timestamp = System.currentTimeMillis();
    }


    public void process() throws Exception {
        Config.LOG_INFO("startdatagram ===========================");
        //Config.LOG_INFO("source ip = %s size = %d", sourceIP, size);

        //java use network endian
        version = buffer.getInt();

        if (version != 5) {
            Config.LOG_ERROR("not supported sflow version %d", version);
            return;
        }

        process_agent_ip();

        //sub agent id, ignore
        buffer.getInt();

        //datagram sequence number, ignore
        buffer.getInt();

        //switch uptime in ms, ignore
        buffer.getInt();

        //Config.LOG_INFO("uptime = %s", timestamp);

        samples = buffer.getInt();

        //Config.LOG_INFO("samples = %d", samples);

        decode_samples();

        //Config.LOG_INFO("enddatagram ===========================");

    }


    private void process_agent_ip() throws Exception {
        int v = buffer.getInt();
        byte[] bytes;
        switch (v) {
            case 1: {
                //ip v4
                bytes = new byte[4];
                buffer.get(bytes);
                break;
            }
            case 2: {
                //ip v6
                bytes = new byte[16];
                buffer.get(bytes);
            }

            default:
                Config.LOG_ERROR("unknown IP version of the Agent %d", v);
                throw new Exception("invalid datagram");
        }

        agent_address = InetAddress.getByAddress(bytes).getHostAddress();
        //Config.LOG_INFO("agent ip = %s", agent_address);

    }


    private void decode_samples() throws Exception {
        Config.LOG_INFO("------------start sample---------------");
        for (int i = 0; i < samples; ++i) {

            int sampleType = buffer.getInt();
            int sampleLength = buffer.getInt();
            byte[] bytes = new byte[sampleLength];
            buffer.get(bytes);

            SFlowSample sample = null;
            switch (sampleType) {
                case COUNTERS_SAMPLE_TYPE: {
                    sample = new CounterSample(bytes, source_ip, timestamp, false);
                    break;
                }
                case COUNTERS_SAMPLE_EXPANDED_TYPE: {
                    sample = new CounterSample(bytes, source_ip, timestamp,true);
                    break;
                }
                case FLOW_SAMPLE_TYPE:
                case FLOW_SAMPLE_EXPANDED_TYPE:
                    /*skip*/
                    break;
                default: {
                    Config.LOG_ERROR("unknown type %d", sampleType);
                }
            }
            if (sample != null) {
                try {
                    sample.decode();
                    sample.saveToDb();
                } catch (Exception e) {
                    Config.LOG_ERROR("process error :" + e.getMessage());
                    continue;
                }

            }
        }

    }

}


