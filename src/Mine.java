public class Mine extends Square {

    public Mine(int row, int col){
        super(row, col);
    }

    public Mine(String toString){
        super(Parser.constructMine(toString).getR(), Parser.constructMine(toString).getC());
    }
}
