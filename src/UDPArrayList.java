import java.util.ArrayList;

public class UDPArrayList<E> extends ArrayList<E> implements ThreadCompleteListener{

    private UDPListener listener;

    public UDPArrayList(UDPListener listener){
        super();
        this.listener = listener;
        listener.addListener(this);
    }

    @Override
    public boolean add(E data){
        UDPTransmission tx = new UDPTransmission(Settings.ADDRESS, Settings.PORT, data.toString());
        tx.doRun();
        return super.add(data);
    }

    @Override
    public void notifyOfThreadComplete(Thread thread) {
        super.add((E) listener.getData());
    }
}
