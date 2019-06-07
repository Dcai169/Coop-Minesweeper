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

    public void listen() {
        try {
            System.out.println("Listening for Data");
            while (true) { //data == null
                socket.receive(packet);
                data = new java.lang.String((packet.getData())).trim();
                System.out.println("RECEIVE:"+data);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doRun() {
        listen();
    }

    public String getData() {
//        String temp = data;
//        data = null;
//        return temp;
        return data;
    }
}
