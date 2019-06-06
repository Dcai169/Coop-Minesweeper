import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeper extends JPanel {

    private UDPArrayList<Action> localActions;
    private boolean gameState;
    private int width, height, totalMines;
    private int mX, mY;
    private int r, c;
    private Board board;
    private UDPListener listener;

    public static final int SIZE = Settings.SIZE;

    public MineSweeper(int width, int height, boolean isServer) {
        this.width = width;
        this.height = height;
        this.totalMines = Settings.TOTAL_MINES; //((width/SIZE)*(height/SIZE))/5;
        this.gameState = true;
        this.board = new Board(width, height);
        this.listener = new UDPListener(Settings.ADDRESS, Settings.PORT);
        this.localActions = new UDPArrayList<Action>(listener);
        setupMouseListener();
        setupKeyboardListener();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < board.getBoard().length; r++) {
            for (int c = 0; c < board.getBoard()[0].length; c++) {
                board.getBoard()[r][c].draw(g2, gameState);
            }
        }
        if (winDetect()) {
            drawWin(g2);
        }
//        System.out.println(getWidth());
//        System.out.println(getHeight());
//        System.out.println();
    }

    public void setupMouseListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameState) {
                    if (e.getButton() == 1) {
                        lClick();
                    } else if (e.getButton() == 2) {
                        System.out.println("MMB");
                    }else if (e.getButton() == 3) {
                        rClick();
                    }
                }
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                mX = mouseEvent.getX();
                mY = mouseEvent.getY();

                r = mY / SIZE;
                c = mX / SIZE;
            }
        });
    }

    public void setupKeyboardListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if (gameState) {
                    if (key == 81) {
                        revealBoard();
                        localActions.add(new Action(r, c, "reveal"));
                    } else if (key == 69) {
                        hideBoard();
                        localActions.add(new Action(r, c, "hide"));
                    } else if (key == 87) {
                        board.setupBoard();
                        hideBoard();
                        localActions.add(new Action(r, c, "newGame"));
                    } else if (key == 32) {
                        rClick();
                        localActions.add(new Action(r, c, "rClick"));
                    } else if (key == 16) {
                        lClick();
                        localActions.add(new Action(r, c, "lClick"));
                    } else if (key == 27) {
                        System.exit(0);
                        localActions.add(new Action(r, c, "quit"));
                    }
                } else if (key == 27) {
                    System.exit(0);
                    localActions.add(new Action(r, c, "quit"));
                } else {
                    board.setupBoard();
                    hideBoard();
                    gameState = true;
                    localActions.add(new Action(r, c, "newGame"));
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }

    public void drawWin(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(width/8,height/6,width/5*4,height/3+SIZE/2);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN,(SIZE/2)));
        g2.drawString("Game Over", width/8+(SIZE/4), (height/6)-(SIZE/8)+SIZE);
        g2.drawString("Press any key to continue", width/8+(SIZE/4), (height/6)-(SIZE/8)+SIZE*2);
        gameState = false;
    }

    public void revealBoard() {
        for (int i = 0; i < board.getBoard().length; i++){
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                board.getBoard()[i][j].setRevealed(true);
            }
        }
    }

    public void lClick() {
        if (!board.getBoard()[r][c].isFlagged()) {
            board.lClick(r, c);
        }
        if (board.getBoard()[r][c].isMine()) {
            gameState = false;
            revealBoard();
        }
        Action a = new Action(r, c, "lClick");
        localActions.add(a);
        UDPTransmission transmission = new UDPTransmission(Settings.ADDRESS, Settings.PORT, a.toString());
    }

    public void rClick() {
        board.rClick(r, c);
    }

    public void hideBoard() {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                board.getBoard()[i][j].setRevealed(false);
                board.getBoard()[i][j].setFlagged(false);
            }
        }
        gameState = true;
    }

    public boolean winDetect(){
        int correctFlags = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                Square current = board.getBoard()[i][j];
                if (current.isMine() == current.isFlagged() && current.isFlagged()){
                    correctFlags++;
                }
            }
        }
        return totalMines == correctFlags;
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, SIZE*Settings.WIDTH, SIZE*Settings.HEIGHT + 22); //(x, y, w, h) 22 due to title bar.

        MineSweeper panel = new MineSweeper(SIZE*Settings.WIDTH, SIZE*Settings.HEIGHT, true);
//        System.out.println(SIZE*Settings.WIDTH);
//        System.out.println(SIZE*Settings.HEIGHT);
//        window.setSize(SIZE*Settings.WIDTH+16, SIZE*Settings.HEIGHT+39);
//        window.setSize(SIZE*Settings.WIDTH, SIZE*Settings.HEIGHT);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
//        window.setResizable(true); // false
        window.setSize(SIZE*Settings.WIDTH+6, SIZE*Settings.HEIGHT+29);
        window.setResizable(false);
    }
}