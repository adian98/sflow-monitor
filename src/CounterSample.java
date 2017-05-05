import config.Config;
import counterrecord.HostDescription;
import counterrecord.VirtNodeInfo;

import java.nio.ByteBuffer;
import counterrecord.*;

public class CounterSample implements SFlowSample {
    private static final int HOST_DESCRIPTION_TYPE = 0X0 | 2000;
    private static final int HOST_ADAPTERS_TYPE = 0X0 | 2001;
    private static final int HOST_PARENT_TYPE = 0X0 | 2002;
    private static final int HOST_CPU_TYPE = 0X0 | 2003;
    private static final int HOST_MEMORY_TYPE = 0X0 | 2004;
    private static final int HOST_DISK_IO_TYPE = 0X0 | 2005;
    private static final int HOST_NET_IO_TYPE = 0X0 | 2006;
    private static final int VIRT_NODE_TYPE = 0X0 | 2100;
    private static final int VIRT_CPU_TYPE = 0X0 | 2101;
    private static final int VIRT_MEMORY_TYPE = 0X0 | 2102;
    private static final int VIRT_DISK_IO_TYPE = 0X0 | 2103;
    private static final int VIRT_NET_IO_TYPE = 0X0 | 2104;

    private ByteBuffer buffer;
    private boolean isExpanded;
    private int idType;
    private int idIndex;
    private int records;
    private String sourceIp;
    private long timestamp;

    public CounterSample(byte[] bytes, String sourceIp, long timestamp, boolean isExpanded) {
        this.buffer = ByteBuffer.wrap(bytes);
        this.isExpanded = isExpanded;
        this.sourceIp = sourceIp;
        this.timestamp = timestamp;
    }

    @Override
    public void decode() throws Exception {
        int sequenceNumb = buffer.getInt();
        //Config.LOG_INFO("sample sequence number = %d", sequenceNumb);

        if (isExpanded) {
            idType = buffer.getInt();
            idIndex = buffer.getInt();
        } else {
            int id = buffer.getInt();
            idType = id >> 24;
            idIndex = id & 0x00ffffff;
        }
        records = buffer.getInt();

        //Config.LOG_INFO("records = %d", records);

        for (int i = 0; i < records; ++i) {
            int type = buffer.getInt();
            int len = buffer.getInt();

            //20 bit enterprise & 12 bit format
            int enterprise = type >> 12;
            int format = type & 0x00000FFF;

            byte[] bytes = new byte[len];
            buffer.get(bytes);

            CounterRecord record = null;
            //Config.LOG_ERROR("type %d", enterprise | format);

            switch (enterprise | format) {
                case HOST_DESCRIPTION_TYPE:
                    record = new HostDescription(bytes, sourceIp, timestamp);
                    break;
                case VIRT_NODE_TYPE:
                    record = new VirtNodeInfo(bytes, sourceIp, timestamp);
                    break;
                case VIRT_CPU_TYPE:
                    record = new VirtCpuInfo(bytes, sourceIp, timestamp);
                    break;
                case VIRT_MEMORY_TYPE:
                    record = new VirtMemoryInfo(bytes, sourceIp, timestamp);
                    break;
                case VIRT_DISK_IO_TYPE:
                    record = new VirtDiskIoInfo(bytes, sourceIp, timestamp);
                    break;
                case VIRT_NET_IO_TYPE:
                    record = new VirtNetIoInfo(bytes, sourceIp, timestamp);
                    break;
                case HOST_ADAPTERS_TYPE:
                case HOST_PARENT_TYPE:
                    /*skip*/
                    break;
                case HOST_CPU_TYPE:
                    record = new HostCpuInfo(bytes, sourceIp, timestamp);
                    break;
                case HOST_MEMORY_TYPE:
                    record = new HostMemoryInfo(bytes, sourceIp, timestamp);
                    break;
                case HOST_DISK_IO_TYPE:
                    record = new HostDiskIoInfo(bytes, sourceIp, timestamp);
                    break;
                case HOST_NET_IO_TYPE:
                    record = new HostNetIoInfo(bytes, sourceIp, timestamp);
                    break;
                case 1:
                case 1005:
                case 2007:
                case 2008:
                case 2009:
                case 2010:
                    /*skip*/
                    break;

                default:
                    Config.LOG_ERROR("not supported type %d", enterprise | format);
                    break;

            }

            try {
                if (record != null) {
                    record.decode();
                    Config.LOG_INFO(record.toString());
                }
            } catch (Exception e) {
                Config.LOG_ERROR(e.getMessage());
            }

        }




    }
}