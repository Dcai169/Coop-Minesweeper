import java.beans.PropertyChangeEvent;
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
        setupPropertyChangeListener();
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

    public void setupPropertyChangeListener() {
        addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                System.out.println(propertyChangeEvent.getPropertyName()+" modified");
                System.out.println("Old: "+propertyChangeEvent.getOldValue());
                System.out.println("New: "+propertyChangeEvent.getNewValue());
                String data = Settings.LISTENER.getData();

//                System.out.println("====[Minesweeper.propertyChange]====");
//                System.out.println(EncodedObject.getHeader(data));
//                System.out.println(EncodedObject.getBody(data));
//                System.out.println();

                if (data.contains("Action")){
                    System.out.println("Action Object Detected");
                    System.out.println(EncodedObject.constructAction(EncodedObject.getBody(data)));
                } else if (data.contains("Board")){
                    System.out.println("Board Object Detected");
                    System.out.println(EncodedObject.constructBoard(data));
                } else {
                    System.out.println("====[Minesweeper.propertyChange]====");
                    System.out.println(EncodedObject.getHeader(data));
                    System.out.println(EncodedObject.getBody(data));
                    System.out.println();
                }
            }
        });
    }
}
