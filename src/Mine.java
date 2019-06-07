public class Mine extends Square {

    private int row, col;

    public Mine(int row, int col) {
        super(row, col);
        this.row = row;
        this.col = col;
        this.setMine(true);
    }

    public String toString(){
        return String.format("r{%d}c{%d}", row, col);
    }
}
