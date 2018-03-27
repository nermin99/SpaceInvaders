import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GameView extends JFrame implements KeyListener {
    private Graphics2D g;
    private Canvas gameCanvas;
    private GameLogic gameLogic;
    private BufferStrategy backBuffer;
    public boolean gameRunning = true;

    public int canvasWidth = 800;
    public int canvasHeight = 600;

    public GameView() {
        super("Space Invader");
        addKeyListener(this);
        gameLogic = new GameLogic(this);

        gameLogic.getKeyDown().put("left", false);
        gameLogic.getKeyDown().put("right", false);
        gameLogic.getKeyDown().put("space", false);

        createWindow();
        gameLogic.loadObjects();
        gameLogic.run();
    }

    public void createWindow() {
        gameCanvas = new Canvas();
        gameCanvas.setFocusable(false);
        gameCanvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        this.add(gameCanvas);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameCanvas.createBufferStrategy(3);
        backBuffer = gameCanvas.getBufferStrategy();

        // Kod för att initiera den långsamma g.drawString innan gameloopen börjar
        g = (Graphics2D)backBuffer.getDrawGraphics();
        g.drawString("",0,0);
    }

    public void render() {
        g = (Graphics2D)backBuffer.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0, gameCanvas.getWidth(), gameCanvas.getHeight());

        ArrayList<Entity> entityList = gameLogic.getEntityList();

        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).draw(g);
        }

        // Kill-counter
        g.setFont(new Font("TimesNewRoman", Font.BOLD, 24));
        g.setColor(Color.white);
        g.drawString(gameLogic.eliminated + "/" + gameLogic.n, 5, 30);

        g.dispose();
        backBuffer.show();
    }

    public void winOrLoose(String str, Color color, int xPos) {
        g = (Graphics2D)backBuffer.getDrawGraphics();
        g.setColor(color);
        g.setFont(new Font("TimesNewRoman", Font.BOLD, 42));
        g.drawString(str, xPos, 300);
        g.dispose();
        backBuffer.show();

        gameRunning = false;
    }

    /* Spelets tangentbordslyssnare */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            gameLogic.getKeyDown().put("space", true);
        }
        if (key == KeyEvent.VK_LEFT) {
            gameLogic.getKeyDown().put("left", true);
        } else if (key == KeyEvent.VK_RIGHT) {
            gameLogic.getKeyDown().put("right", true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            gameLogic.getKeyDown().put("space", false);
        }
        if (key == KeyEvent.VK_LEFT) {
            gameLogic.getKeyDown().put("left", false);
        } else if (key == KeyEvent.VK_RIGHT) {
            gameLogic.getKeyDown().put("right", false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new GameView();
    }
}
