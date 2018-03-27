import javax.swing.*;
import java.awt.*;

public class ShipEntity extends Entity{
    public MissileEntity missile = null;

    public ShipEntity(Image image, double xPos, double yPos, int speed) {
        super(image, xPos, yPos, speed);
    }

    /**
     * Ändrar läget i x-led
     * @param deltaTime tidsskillnaden
     */
    @Override
    public void move(long deltaTime) {
        xPos += dx*(deltaTime/1000000000.0)*speed;

        if (missile != null && missile.getActive()) {
            missile.move(deltaTime);
        }
    }

    /**
     * Ritar ut missil och skepp
     * @param g grafikobjekt
     */
    @Override
    public void draw(Graphics2D g) {
        if (missile != null && missile.getActive()) {
            missile.draw(g);
        }
        super.draw(g);
    }

    /**
     * Avfyrar en missil sålänge det inte redan finns en avfyrad på skärmen
     * Nödvändigt med returtyp? (setActive() <- viktiga)
     * @return sant eller falskt
     */
    public boolean tryToFire() {
        if (missile == null || !missile.getActive()) {
            missile = new MissileEntity(new ImageIcon(getClass().getResource("missile.png")).getImage(), xPos, yPos, 600);

            // Centrerar missilen relativt till skeppet
            double centerPos = getxPos() + getWidth()/2 - missile.getWidth()/2;
            missile.setxPos(centerPos);

            missile.setActive(true);
            return true;
        } else {
            return false;
        }
    }
}
