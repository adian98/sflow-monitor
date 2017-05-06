package counterrecord;
import config.Config;

import java.nio.ByteBuffer;
import java.util.HashMap;


public class HostDescription extends CounterRecord {
    private String hostname;     /* hostname, empty if unknown */
    private String uuid;         /* 16 bytes binary UUID, empty if unknown */
    private String machine_type; /* the processor family */
    private String os_name;      /* Operating system */
    private String os_release;   /* e.g. 2.6.9-42.ELsmp,xp-sp3, empty if unknown */

    public HostDescription(byte[] bytes, String sourceIP, long timestamp) {
        super(bytes, sourceIP, timestamp);
    }


    @Override
    public void decode() throws Exception {
        hostname = Utils.getString(buffer, 64);
        //Config.LOG_INFO("hostname = %s", hostname);
        decodeUuid();

        decodeMachineType();

        decodeOsName();

        os_release = Utils.getString(buffer, 32);
    }

    private void decodeUuid() throws Exception {
        byte[] bytes = new byte[16];
        buffer.get(bytes);
        StringBuilder builder = new StringBuilder();

        for(int i= 0; i < bytes.length; ++i) {
            if (i < bytes.length -1) {
                builder.append(String.format("%2x-", bytes[i]));
            } else {
                builder.append(String.format("%2x", bytes[i]));
            }
        }

        uuid = builder.toString();
    }

    private void decodeMachineType() throws Exception {
        int type = buffer.getInt();
        switch (type) {
            case 0:
                machine_type = "unknown";
                break;
            case 1:
                machine_type = "other";
                break;
            case 2:
                machine_type = "x86";
                break;
            case 3:
                machine_type = "x86_64";
                break;
            case 4:
                machine_type = "ia64";
                break;
            case 5:
                machine_type = "sparc";
                break;
            case 6:
                machine_type = "alpha";
                break;
            case 7 :
                machine_type = "powerpc";
                break;
            case 8:
                machine_type = "m68k";
                break;
            case 9:
                machine_type = "mips";
                break;
            case 10:
                machine_type = "arm";
                break;
            case 11:
                machine_type = "hppa";
                break;
            case 12:
                machine_type = "s390";
                break;

            default:
                machine_type = "unknown";
                break;
        }
    }

    private void decodeOsName() throws Exception {
        int type = buffer.getInt();
        switch (type) {
            case 1:
                os_name = "other";
                break;
            case 2:
                os_name = "linux";
                break;
            case 3:
                os_name = "windows";
                break;
            case 4:
                os_name = "darwin";
                break;
            case 5:
                os_name = "hpux";
                break;
            case 6:
                os_name = "aix";
                break;
            case 7:
                os_name = "dragonfly";
                break;
            case 8:
                os_name = "freebsd";
                break;
            case 9:
                os_name = "netbsd";
                break;
            case 10:
                os_name = "openbsd";
                break;
            case 11:
                os_name = "osf";
                break;
            case 12:
                os_name = "solaris";
                break;
            default:
                os_name = "unknown";
                break;
        }
    }

    @Override
    protected HashMap<String, Object> getMap() {
        HashMap<String, Object> map = super.getMap();
        map.put("hostname", hostname);
        map.put("uuid", uuid);
        map.put("machine_type", machine_type);
        map.put("os_name", os_name);
        map.put("os_release", os_release);
        return map;
    }

    static public String schema() {
        return "CREATE TABLE host_description (" +
                "host_ip TEXT NOT NULL, " +
                "timestamp INTEGER, " +
                "hostname TEXT, " +
                "uuid TEXT, " +
                "machine_type TEXT, " +
                "os_name TEXT, " +
                "os_release TEXT );";
    }
}