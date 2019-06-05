import java.util.ArrayList;

public class UDPArrayList<String> extends ArrayList<String>{

    private UDPListener listener;

    public UDPArrayList(UDPListener listener){
        super();
        this.listener = listener;
    }

    @Override
    public boolean add(String obj){

        return super.add(obj);
    }

    public boolean networkAdd(){
        listener.start();
        super.add((String) listener.getData());
        return false;
    }
}
