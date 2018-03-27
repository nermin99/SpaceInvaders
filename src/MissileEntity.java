import java.awt.*;

public class MissileEntity extends Entity {
    public MissileEntity(Image image, double xPos, double yPos, int speed) {
        super(image, xPos, yPos, speed);
        dx = 0;
        dy = -1;
        this.setActive(false);
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
