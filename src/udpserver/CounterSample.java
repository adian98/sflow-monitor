package udpserver;

import counter_record.HostDescription;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.util.HashSet;
import counter_record.*;
import db.DB;
import log.LOG;

public class CounterSample implements SFlowSample {
    private static final int HOST_DESCRIPTION_TYPE = 0X0 | 2000;
    private static final int HOST_ADAPTERS_TYPE = 0X0 | 2001;
    private static final int HOST_PARENT_TYPE = 0X0 | 2002;
    private static final int HOST_CPU_TYPE = 0X0 | 2003;
    private static final int HOST_MEMORY_TYPE = 0X0 | 2004;
    private static final int HOST_DISK_IO_TYPE = 0X0 | 2005;
    private static final int HOST_NET_IO_TYPE = 0X0 | 2006;
    private static final int HOST_NODE_TYPE = 0X0 | 2100;
    private static final int VIRT_CPU_TYPE = 0X0 | 2101;
    private static final int VIRT_MEMORY_TYPE = 0X0 | 2102;
    private static final int VIRT_DISK_IO_TYPE = 0X0 | 2103;
    private static final int VIRT_NET_IO_TYPE = 0X0 | 2104;

    private ByteBuffer buffer;
    private boolean is_expanded;
    private int id_type;
    private int id_index;
    private int records;
    private String source_ip;
    private long timestamp;

    private HashSet<HostCounterRecord> host_records;
    private HashSet<VirtCounterRecord> virt_records;
    private HostDescription description;

    public CounterSample(byte[] bytes, String source_ip, long timestamp, boolean is_expanded) {
        this.buffer = ByteBuffer.wrap(bytes);
        this.is_expanded = is_expanded;
        this.source_ip = source_ip;
        this.timestamp = timestamp;
        host_records = new HashSet<HostCounterRecord>();
        virt_records = new HashSet<VirtCounterRecord>();
    }

    @Override
    public void decode() throws Exception {
        int sequenceNumb = buffer.getInt();

        if (is_expanded) {
            id_type = buffer.getInt();
            id_index = buffer.getInt();
        } else {
            int id = buffer.getInt();
            id_type = id >> 24;
            id_index = id & 0x00ffffff;
        }
        records = buffer.getInt();

        for (int i = 0; i < records; ++i) {
            int type = buffer.getInt();
            int len = buffer.getInt();

            //20 bit enterprise & 12 bit format
            int enterprise = type >> 12;
            int format = type & 0x00000FFF;

            byte[] bytes = new byte[len];
            buffer.get(bytes);

            switch (enterprise | format) {
                case HOST_DESCRIPTION_TYPE:
                    description = HostDescription.fromBytes(bytes, source_ip, timestamp);
                    break;
                case VIRT_CPU_TYPE:
                    virt_records.add(VirtCpuInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case VIRT_MEMORY_TYPE:
                    virt_records.add(VirtMemoryInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case VIRT_DISK_IO_TYPE:
                    virt_records.add(VirtDiskIoInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case VIRT_NET_IO_TYPE:
                    virt_records.add(VirtNetIoInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case HOST_ADAPTERS_TYPE:
                case HOST_PARENT_TYPE:
                    /*skip*/
                    break;
                case HOST_CPU_TYPE:
                    host_records.add(HostCpuInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case HOST_MEMORY_TYPE:
                    host_records.add(HostMemoryInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case HOST_DISK_IO_TYPE:
                    host_records.add(HostDiskIoInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case HOST_NET_IO_TYPE:
                    host_records.add(HostNetIoInfo.fromBytes(bytes, source_ip, timestamp));
                    break;
                case HOST_NODE_TYPE:
                    host_records.add(HostNodeInfo.fromBytes(bytes, source_ip, timestamp));
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
                    LOG.ERROR("not supported type %d", enterprise | format);
                    break;
            }
        }
    }

    @Override
    public void saveToDb(Connection conn) throws Exception {
        if (description == null) {
            return;
        }
        if (!host_records.isEmpty() && virt_records.isEmpty()) {
            //host record
            description.saveToDb(conn);
            for (HostCounterRecord record : host_records) {
                record.saveToDb(conn);
            }
        } else if (!virt_records.isEmpty() && host_records.isEmpty()){
            //virt record
            String host_name = description.getHostName();
            assert host_records.isEmpty();
            VirtDescription description = new VirtDescription(source_ip, timestamp, host_name);
            description.saveToDb(conn);

            for (VirtCounterRecord record : virt_records) {
                record.setHostName(host_name);
                record.saveToDb(conn);
            }
        } else {
            //never happened
            LOG.ERROR("both host and virt info %s", source_ip);
        }

    }
}