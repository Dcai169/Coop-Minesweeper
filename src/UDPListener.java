import java.lang.*;
import java.net.*;

public class UDPListener extends NotifyingThread{
    private DatagramSocket socket;
    private DatagramPacket packet;
    private String data, name;
    private boolean continueListening;

    public UDPListener(String ip, int port, boolean continueListening) {
        try {
            socket = new DatagramSocket(port, InetAddress.getByName(ip));
            socket.setBroadcast(true);
            byte[] buffer = new byte[512];
            packet = new DatagramPacket(buffer, buffer.length);
        } catch (Exception e){
            e.printStackTrace();
        }
        this.data = null;
        this.continueListening = continueListening;
        this.name = name;
    }

    public String listen() {
        try {
            while (continueListening) {
                socket.receive(packet);
                data = new java.lang.String((packet.getData())).trim();
//                System.out.println("RECEIVE: "+data);
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
        if (continueListening){
            doRun();
        }
    }

    public String getData() {
        return data;
    }

    public String checkName(String toString){
        String key = toString.substring(0, toString.indexOf(":"));
        String value = toString.substring(toString.indexOf(":")+1, toString.indexOf(";"));
        if (key.equals("name")){
            if (!value.equals(name)) {
                return toString.substring(toString.indexOf(";") + 1);
            }
        }
        return null;

    }

    public void setContinueListening(boolean continueListening) {
        this.continueListening = continueListening;
    }

    public String get_Name() {
        return name;
    }

    public void set_Name(String name) {
        this.name = name;
    }
}
