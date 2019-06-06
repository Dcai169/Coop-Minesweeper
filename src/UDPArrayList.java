import java.util.ArrayList;

public class UDPArrayList<E> extends ArrayList<E> {

    private UDPListener listener;

    public UDPArrayList(UDPListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public boolean add(E data){
        UDPTransmission tx = new UDPTransmission(Settings.ADDRESS, Settings.PORT, data.toString());
        tx.doRun();
        return super.add(data);
    }

    @Override
    public void add(int i, E data){
        if (i >= 0){
            UDPTransmission tx = new UDPTransmission(Settings.ADDRESS, Settings.PORT, data.toString());
            tx.doRun();
            super.add(i, data);
        } else {
            super.add(-i,data);
        }
    }

    public UDPListener getListener() {
        return listener;
    }
}
