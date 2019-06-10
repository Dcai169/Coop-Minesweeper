import java.io.IOException;
import java.lang.*;
import java.net.*;

public class UDPListener extends Thread{
    private DatagramSocket socket;
    private DatagramPacket packet;
    private String data;
    private boolean modified;

    public UDPListener(String ip, int port) {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName(ip));
            socket.setBroadcast(true);
            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
        } catch (Exception e){
            e.printStackTrace();
        }
//        setupPropertyChangeListener();
        this.data = null;
        this.modified = false;
    }

    public boolean listen(boolean quit) {
        try {
            while (true) {
                socket.receive(packet);
                setData(new java.lang.String((packet.getData())).trim());
                modified = true;
//                System.out.println("RECEIVE: "+data);
                if (quit){
                    return true;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        listen(false);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
