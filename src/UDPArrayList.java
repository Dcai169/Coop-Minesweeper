import java.util.ArrayList;

public class UDPArrayList<E> extends ArrayList<E> {

    private UDPListener listener;

    public UDPArrayList(UDPListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public boolean add(E data){
        UDPTransmission tx = new UDPTransmission(Settings.TARGET_ADDRESS, Settings.PORT, EncodedObject.getPacket(Settings.NAME, data));
        tx.doRun();
        return super.add(data);
    }

    public void localAdd(E data){
        super.add(data);
    }

    public UDPListener getListener() {
        return listener;
    }
}
