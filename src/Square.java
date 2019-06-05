import java.awt.*;

public class Square {

    private boolean isMine, isRevealed, isFlagged;
    private int neighborMines;
    private int r, c, size;
    private static final Color LIT = new Color(150,150,150);
    private static final Color SHADOW = new Color(100, 100, 100);
    private static final Font FONT = new Font("Arial", Font.PLAIN,Settings.SIZE/2);

    public Square(int r, int c) {
        this.isMine = false;
        this.r = r;
        this.c = c;
        this.isRevealed = false;
        this.isFlagged = false;
        this.size = MineSweeper.SIZE;
        neighborMines = 0;
    }

    public void draw(Graphics2D g2, boolean gameState) {
        drawTile(g2);
        if (gameState) {
            if (isFlagged) {
                drawFlag(g2);
            } else {
                if (isMine && isRevealed) {
                    drawBomb(g2);
                } else if (isRevealed){
                    drawNeighbors(g2);
                }
            }
        } else {
            if (isMine && !isFlagged) {
                drawBomb(g2);
            } else if (isMine){
                drawFlag(g2);
            } else if (isFlagged) {
                drawFlag(g2);
                drawCross(g2);
            } else {
                drawNeighbors(g2);
            }
        }
        //g2.setColor(Color.BLACK);
        //g2.drawRect(c * size, r * size, size, size);
    }



    public void drawFlag(Graphics2D g2) {
        g2.setFont(FONT);
        g2.drawString("🚩",
                c * size+size/4,
                r*size+size/4*3);
    }

    public void drawBomb(Graphics2D g2) {
        g2.setFont(FONT);
        g2.drawString("💣",
                c * size + size/4,
                r * size+size/4*3);
    }

    public void drawCross(Graphics2D g2) {
        g2.setFont(FONT);
        g2.drawString("❌",
                c * size+size/4,
                r*size+size/4*3);
    }

    public void drawNeighbors(Graphics2D g2){
        if (neighborMines != 0) {
            if (neighborMines == 1){
                g2.setColor(Color.BLUE);
            } else if (neighborMines == 2){
                g2.setColor(Color.GREEN);
            } else if (neighborMines == 3){
                g2.setColor(Color.RED);
            } else if (neighborMines == 4){
                g2.setColor(Color.MAGENTA);
            } else if (neighborMines == 5){
                g2.setColor(Color.ORANGE);
            } else if (neighborMines == 6){
                g2.setColor(Color.YELLOW);
            } else if (neighborMines == 7){
                g2.setColor(Color.CYAN);
            } else if (neighborMines == 8){
                g2.setColor(Color.PINK);
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.setFont(FONT);
            g2.drawString(
                    Integer.toString(neighborMines),
                    c * size + size/13*6,
                    r * size + size/7*5
            );
        }
    }

    public void drawTile(Graphics2D g2){
        int[] topTriX = {c * size, (c+1) * size, c * size};
        int[] topTriY = {r * size, r * size, (r+1) * size};

        int[] bottomTriX = {(c+1) * size, (c+1) * size, c * size};
        int[] bottomTriY = {r * size, (r+1) * size, (r+1) * size};

        Polygon topTri = new Polygon(topTriX, topTriY, 3);
        Polygon bottomTri = new Polygon(bottomTriX, bottomTriY, 3);

        int indent = 10;

        if (isRevealed) {
            g2.setColor(SHADOW);
            g2.fillPolygon(topTri);
            g2.setColor(LIT);
            g2.fillPolygon(bottomTri);
            g2.setColor(Color.GRAY);
            g2.fillRect(c * size+size/indent,
                    r * size+size/indent,
                    size-size/(indent/2),
                    size-size/(indent/2));
        } else {
            g2.setColor(LIT);
            g2.fillPolygon(topTri);
            g2.setColor(SHADOW);
            g2.fillPolygon(bottomTri);
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(c * size+size/indent,
                    r * size+size/indent,
                    size-size/(indent/2),
                    size-size/(indent/2));
        }
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public String toString(){
        return String.format("r:%d;c:%d;isMine:%s;", r, c, isMine);
    }
}
