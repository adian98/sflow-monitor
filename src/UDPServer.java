import config.Config;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by cloud on 17-4-28.
 */
public class UDPServer implements Runnable{

    private static int MAX_SFLOW_PKT_SIZ = 65536;

    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        DatagramChannel channel;

        try {
            channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(port));
        } catch (Exception e) {
            Config.LOG_INFO(e.getMessage());
            return;
        }

        while(true) {
            ByteBuffer buf = ByteBuffer.allocate(MAX_SFLOW_PKT_SIZ);
            buf.clear();
            InetSocketAddress socketAddress;
            try {
                socketAddress = (InetSocketAddress)channel.receive(buf);
                buf.flip();

            } catch (IOException e) {
                Config.LOG_INFO(e.getMessage());
                continue;
            }

            SFlowDatagram datagram = new SFlowDatagram(buf, socketAddress.getAddress());

            try {
                datagram.process();
            }catch (Exception e) {
                Config.LOG_ERROR(e.getMessage());
            }

        }

    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new UDPServer(6343));
        thread.start();
        thread.join();
    }
}
