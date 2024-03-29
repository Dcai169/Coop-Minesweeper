public class Action {

    private int r, c;
    private String type;

    public Action(int r, int c, String type){
        this.r = r;
        this.c = c;
        this.type = type;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("r:%d;c:%d;type:%s;", r, c, type);
    }


}
