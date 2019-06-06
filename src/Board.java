import java.util.ArrayList;

public class Board implements ThreadCompleteListener {
    private int width, height;
    private Square[][] board;
    private UDPArrayList<Mine> mines;
    public static final int SIZE = Settings.SIZE;
    public static final int TOTAL_MINES = Settings.TOTAL_MINES;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.mines = new UDPArrayList<Mine>(Settings.LISTENER);
        this.mines.getListener().addListener(this);
        generateBoard();
    }

    public Board(int width, int height, UDPArrayList<Mine> mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public void generateBoard() {
        int minesPlanted = 0;
        board = new Square[height/SIZE][width/SIZE];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Square(i, j);
            }
        }

        while (minesPlanted <= TOTAL_MINES-1){
            int random = (int)(Math.random() * 100);
            if (random > 90 && TOTAL_MINES >= minesPlanted) {
                int randR = (int)(Math.random() * (height/SIZE));
                int randC = (int)(Math.random() * (width/SIZE));
                Mine mine = new Mine(randR, randC);
                board[randR][randC] = mine;
                board[randR][randC].setMine(true);
                mines.localAdd(mine);
                minesPlanted++;
            }
        }

        for (int k = 0; k < board.length; k++) {
            for (int l = 0; l < board[0].length; l++) {
                board[k][l].setNeighborMines(numNeighborMines(k, l));
            }
        }
        UDPTransmission newBoardTX = new UDPTransmission(Settings.TARGET_ADDRESS, Settings.PORT, EncodedObject.getPacket(Settings.NAME, this));
        newBoardTX.start();
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

    @Override
    public void notifyOfThreadComplete(Thread thread) {
//        if (Settings.LISTENER.getData().contains("mines")) {
//            Board board = EncodedObject.constructBoard(Settings.LISTENER.getData());
//            mines.clear();
//            for (Mine mine : board.getMines()) {
//                mines.localAdd(mine);
//            }
//            setBoardDimensions(board.getHeight(), board.getWidth());
//        }
    }

    public void setBoardDimensions(int r, int c){
        board = new Square[r][c];
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
