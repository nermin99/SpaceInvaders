import java.awt.*;

public class AlienEntity extends Entity {
    public AlienEntity(Image image, double xPos, double yPos, int speed) {
        super(image, xPos, yPos, speed);
        dx = 0;
        dy = 1;
    }

    /**
     * Ritar ut alien om aktiv
     * Nödvändig? Då en eliminerad alien tas bort från alienList
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {
        if (getActive()) {
            super.draw(g);
        }
    }

    /**
     * Ändrar läget i y-led
     * @param deltaTime tidsskillnaden
     */
    @Override
    public void move(long deltaTime) {
        yPos += dy*(deltaTime/1000000000.0)*speed;
    }
}
