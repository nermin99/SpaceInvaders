import java.awt.*;

public abstract class Entity {
    private Image image;
    protected double xPos, yPos;
    protected int speed;
    protected int dx = 0, dy = 0;
    private boolean active = true;
    private Rectangle rec;

    /**
     * Konstruktor
     */
    public Entity (Image image, double xPos, double yPos, int speed){
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;

        rec = new Rectangle((int) xPos, (int) yPos, getWidth(), getHeight());
    }

    /**
     * Ritar bilden på ytan g
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int)xPos, (int)yPos, null);
    }

    /**
     * Vilken riktning i x-led
     * @param dx 0 = stilla, 1 = höger, -1 = vänster
     */
    public void setDirectionX(int dx) {
        this.dx = dx;
    }

    /**
     * Vilken riktning i y-led
     * @param dy 0 = stilla, 1 = ner, -1 = upp
     */
    public void setDirectionY(int dy) {
        this.dy = dy;
    }

    /**
     * Gör förflyttningen, ändrar xPos och yPos
     */
    public abstract void move(long deltaTime);


    /**
     * Returnerar bredden på entiteten
     * @return bredden
     */
    public int getWidth(){
        return image.getWidth(null);
    }

    /**
     * Returnerar höjden på entiteten
     * @return höjden
     */
    public int getHeight() {
        return image.getHeight(null);
    }

    /**
     * Returnerar om alien är eliminerad
     * @return sant eller falskt
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Anger om alien är eleminerad eller inte
     * @param active sant eller falskt
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returnerar x-kordinaten för entiteten
     * @return x-kordinaten
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * Sätter värdet för x-kordinaten för entiteten
     * @param xPos x-kordinaten
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * Returnerar y-kordinaten för entiteten
     * @return y-kordinaten
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * Ändrar rektangels position till entitetens position
     * @return rektangeln
     */
    public Rectangle getRectangle() {
        rec.setLocation((int)xPos, (int)yPos);
        return rec;
    }

    /**
     * Kollar om rektanglarna, entiteterna, överlappar/krockat
     * @param entity den andra entiteten
     * @return sant eller falskt
     */
    public boolean colission(Entity entity) {
        getRectangle();
        return rec.intersects(entity.getRectangle());
    }
}
