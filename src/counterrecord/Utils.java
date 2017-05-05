package counterrecord;

import java.nio.ByteBuffer;

public class Utils {
    public static String getString(ByteBuffer buffer,  int max_len) throws Exception {
        int strlen = buffer.getInt();
        if (strlen == 0) {
            return "unknown";
        }

        int len = Math.min(strlen, max_len);
        byte[] bytes = new byte[len];

        buffer.mark();
        buffer.get(bytes);
        buffer.reset();

        Utils.skipBytes(buffer, len);

        return new String(bytes, "UTF-8");
    }

    public static void skipBytes(ByteBuffer buffer, int n) throws Exception {
        //内存对齐
        int v = n % 4;
        if (v != 0) {
            n += (4 - v);
        }
        int p = buffer.position() + n;
        buffer.position(p);
    }

    public static long bufferGetUint32(ByteBuffer buffer) {
        //java 没有 uint32_t ,用 long 储存
        ByteBuffer buf= ByteBuffer.allocate(8);
        buf.putInt(0);
        buf.putInt(buffer.getInt());
        buf.flip();
        return buf.getLong();
    }
}
