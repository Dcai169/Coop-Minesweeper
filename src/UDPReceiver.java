import java.io.IOException;
import java.lang.*;
import java.net.*;

public class UDPReceiver {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private byte[] buffer;

    public UDPReceiver(String ip, int port) throws IOException {
        socket = new DatagramSocket(port, InetAddress.getByName(ip));
        socket.setBroadcast(true);
        buffer = new byte[512];
        packet = new DatagramPacket(buffer, buffer.length);

    }

    public String listen() throws IOException {
        String data = null;
        while (data == null) {
            socket.receive(packet);
            data = new String((packet.getData())).trim();
            System.out.println(data);
        }
        return data;
    }

    public static void main(String[] args) {
        try {
            UDPReceiver udpReceiver = new UDPReceiver("127.0.0.1", 5005);
            udpReceiver.listen();
        } catch (IOException e){
            System.exit(1);
        }

    }
}
