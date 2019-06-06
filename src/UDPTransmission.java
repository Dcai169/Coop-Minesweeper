import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPTransmission extends NotifyingThread {

    private String ip_address;
    private int ip_port;
    private String data;

    public UDPTransmission(String address, int port, String data){
        this.ip_address = address;
        this.ip_port = port;
        this.data = data;
    }

    public void serve(){
        try {
//            System.out.println("SEND: "+data);
            byte[] buffer = data.getBytes();
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip_address), ip_port);
            socket.send(packet);
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void doRun() {
        serve();
    }
}
