import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.lang.*;
import java.net.*;

public class UDPListener extends Thread{
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
            while (true) {
                socket.receive(packet);
                setData(new java.lang.String((packet.getData())).trim());
                System.out.println("RECEIVE:"+data);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        listen();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        String oldData = this.data;
        this.data = data;
        this.pcs.firePropertyChange("data", oldData, data);
    }

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
}
