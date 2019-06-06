import java.lang.*;
import java.net.*;

public class UDPListener extends NotifyingThread{
    private DatagramSocket socket;
    private DatagramPacket packet;
    private String data;

    public UDPListener(String ip, int port) {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName(ip));
            socket.setBroadcast(true);
            byte[] buffer = new byte[512];
            packet = new DatagramPacket(buffer, buffer.length);
        } catch (Exception e){
            e.printStackTrace();
        }
        this.data = null;
    }

    public String listen() {
        try {
            while (data == null) {
                socket.receive(packet);
                data = new java.lang.String((packet.getData())).trim();
//                System.out.println("RECEIVE:"+data);
            }
            return data;
        } catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void doRun() {
        listen();
    }

    public String getData() {
        return data;
    }
}
