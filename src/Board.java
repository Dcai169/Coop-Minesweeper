import java.util.ArrayList;

public class Board {
    private int width, height;
    private Square[][] board;
    private ArrayList<Mine> mines;
    public static final int SIZE = Settings.SIZE;
    public static final int TOTAL_MINES = Settings.TOTAL_MINES;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.mines = new ArrayList<Mine>();
        setupBoard();
    }

    public void setupBoard() {
        int minesPlanted = 0;
        board = new Square[height/SIZE][width/SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square(i, j);
            }
        }

        while (minesPlanted <= TOTAL_MINES-1){
            int random = (int)(Math.random() * 100);
            int randR = (int)(Math.random() * (height/SIZE));
            int randC = (int)(Math.random() * (width/SIZE));
            if (random > 90 && TOTAL_MINES >= minesPlanted) {
                Mine mine = new Mine(randR, randC);
                board[randR][randC] = mine;
                board[randR][randC].setMine(true);
                mines.add(mine);
                minesPlanted++;
            }
        }

        for (int k = 0; k < board.length; k++) {
            for (int l = 0; l < board[0].length; l++) {
                board[k][l].setNeighborMines(numNeighborMines(k, l));
            }
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public ArrayList<Mine> getMines() {
        return mines;
    }

    @Override
    public String toString() {
        return String.format("width:%d;height:%d;mines:%s;", width, height, mines.toString());
    }

    public void lClick(int r, int c) {
        recursiveReveal(r, c);
//        flaggedReveal();
    }

    public void rClick(int r, int c){
        Square square = board[r][c];
        if (!square.isRevealed()) {
            square.setFlagged(!square.isFlagged());
        }
    }

    public void recursiveReveal(int r, int c) {
        Square square = board[r][c];
        if(!square.isRevealed() && !square.isRevealed()) {
            square.setRevealed(true);
            if(square.getNeighborMines() == 0) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i+r >= 0 && j+c >= 0 && i+r < board.length && j+c < board[0].length){
                            if (!board[r+i][c+j].isMine()){
                                lClick(r+i, c+j);
                            }
                        }
                    }
                }
            }
        }
    }

//    public void flaggedReveal() {
//        if(!isRevealed && !isFlagged) {
//            isRevealed = true;
//            if(numNeighborFlags(board) == neighborMines) {
//                for (int i = -1; i < 2; i++) {
//                    for (int j = -1; j < 2; j++) {
//                        if (i+r >= 0 && j+c >= 0 && i+r < board.length && j+c < board[0].length){
//                            if (!board[r+i][c+j].isFlagged){
//                                board[r+i][c+j].lClick();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public int numNeighborMines(int r, int c) {
        int neighborMines = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i+r >= 0 && j+c >= 0 && i+r < board.length && j+c < board[0].length){
                    if (board[r+i][c+j].isMine()){
                        neighborMines++;
                    }
                }
            }
        }
        if (board[r][c].isMine()){
            neighborMines--;
        }
        return neighborMines;
    }

//    public int numNeighborFlags(Square[][] board){
//        int neighborFlags = 0;
//        for (int i = -1; i < 2; i++) {
//            for (int j = -1; j < 2; j++) {
//                if (i+r >= 0 && j+c >= 0 && i+r < board.length && j+c < board[0].length){
//                    if (board[r+i][c+j].isFlagged()){
//                        neighborFlags++;
//                    }
//                }
//            }
//        }
//        if (board[r][c].isFlagged()){
//            neighborFlags--;
//        }
//        return neighborFlags;
//    }
}