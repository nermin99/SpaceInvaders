import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameLogic {
    public int eliminated;
    private ShipEntity ship;
    private GameView gameView;
    private long lastUpdateTime;
    private ArrayList<Entity> entityList;
    private HashMap<String, Boolean> keyDown = new HashMap<>();

    public int n = 5;
    private int fps = 60;
    private int shipSpeed = 400; // [px/s]
    private int alienSpeed = 70;

    public GameLogic(GameView gameView) {
        this.gameView = gameView;
    }

    public void loadObjects() {
        Image shipImg = new ImageIcon(getClass().getResource("/ship.png")).getImage();
        Image alienImg = new ImageIcon(getClass().getResource("/alien.png")).getImage();

        double xShip = gameView.canvasWidth/2 - shipImg.getWidth(null)/2;
        double yShip = gameView.canvasHeight - shipImg.getHeight(null) - 10;
        int alienWidth = alienImg.getWidth(null);


        entityList = new ArrayList<>(n);
        entityList.add(new ShipEntity(shipImg, xShip, yShip, shipSpeed));

        for (int k = 0; k < n; k++) {
            double xAlien = (gameView.canvasWidth*(2*k+1)-n*alienWidth)/(2*n);
            entityList.add(new AlienEntity(alienImg, xAlien, 5, alienSpeed));
        }

        ship = (ShipEntity) (entityList.get(0));
    }

    public void run() {
        lastUpdateTime = System.nanoTime();
        int updateTime = (int)(1000000000.0/fps);

        while (gameView.gameRunning) {
            long deltaTime = System.nanoTime() - lastUpdateTime;

            if (deltaTime >= updateTime) {
                lastUpdateTime = System.nanoTime();
                update(deltaTime);
                gameView.render();
            }
        }
    }

    public void update(long deltaTime) {
        if (ship.missile != null && ship.missile.getActive()) {
            if (ship.missile.getyPos() + ship.missile.getHeight() <= 0) {
                ship.missile.setActive(false);
            }
        }
        if (keyDown.get("space")) {
            ship.tryToFire();
        }

        if (keyDown.get("right")) {
            if (ship.xPos + ship.getWidth() >= gameView.canvasWidth) {
                ship.setDirectionX(0);
            } else {
                ship.setDirectionX(1);
            }
        } else if (keyDown.get("left")) {
            if (ship.xPos <= 0) {
                ship.setDirectionX(0);
            } else {
                ship.setDirectionX(-1);
            }
        } else {
            ship.setDirectionX(0);
        }

        ship.move(deltaTime);

        if (!(entityList.size() <= 1)) {
            if (entityList.get(1).yPos + entityList.get(1).getHeight() < gameView.canvasHeight - 10) {
                for (int i = 1; i < entityList.size(); i++) {
                    entityList.get(i).move(deltaTime);
                }
            }
        }

        checkCollisionAndRemove();
    }

    public void checkCollisionAndRemove() {
        // Om missile träffar alien
        if (ship.missile != null && ship.missile.getActive()) {
            for (int i = 1; i < entityList.size(); i++) {
                if (ship.missile.colission(entityList.get(i))) {
                    ship.missile.setActive(false);
                    entityList.get(i).setActive(false);

                    entityList.remove(i);
                    eliminated++;
                }
            }
        }

        // Om alla aliens eliminerats
        if (entityList.size() <= 1) {
            gameView.render();
            gameView.winOrLoose("Du vann!", Color.green, 300);
        }

        // Om alien träffar ship
        for (int i = 1; i < entityList.size(); i++) {
            if (ship.colission(entityList.get(i))) {
                gameView.winOrLoose("Du förlorade!", Color.red,250);
            }
        }
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public HashMap<String, Boolean> getKeyDown() {
        return keyDown;
    }
}
