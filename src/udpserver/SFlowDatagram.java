package udpserver;

import db.DB;
import log.LOG;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

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

    private ArrayList<SFlowSample> samples_list;


    public SFlowDatagram(ByteBuffer buffer, InetAddress socketAddress) {
        is_v4 = socketAddress instanceof Inet4Address;
        this.buffer = buffer;
        size = buffer.remaining();
        source_ip = socketAddress.getHostAddress();
        timestamp = System.currentTimeMillis();
        samples_list = new ArrayList<SFlowSample>();
    }


    public void decode() throws Exception {
        //java use network endian
        version = buffer.getInt();

        if (version != 5) {
            LOG.ERROR("not supported sflow version %d", version);
            return;
        }

        process_agent_ip();

        //sub agent id, ignore
        buffer.getInt();

        //datagram sequence number, ignore
        buffer.getInt();

        //switch uptime in ms, ignore
        buffer.getInt();

        samples = buffer.getInt();

        decode_samples();
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
                LOG.ERROR("unknown IP version of the Agent %d", v);
                throw new Exception("invalid datagram");
        }

        agent_address = InetAddress.getByAddress(bytes).getHostAddress();
    }


    private void decode_samples() {
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
                    LOG.ERROR("unknown type %d", sampleType);
                }
            }
            if (sample != null) {
                try {
                    sample.decode();
                    samples_list.add(sample);
                } catch (Exception e) {
                    LOG.ERROR("sample decode error :" + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public void saveToDb() throws Exception {
        synchronized (DB.db_lock) {
            //lock the db
            for (SFlowSample sample : samples_list) {
                sample.saveToDb(DB.db_conn);
            }
            DB.db_conn.commit();
        }
    }

}


