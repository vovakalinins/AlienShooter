import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Weapon {
    private int damage;
    private Rectangle hitbox;
    private boolean isActive;
    private SpriteSheet gsprite;
    private Image[] walk;
    private Animation ani;
    private float deltaX, deltaY; // Direction of movement
    private float rotation;

    public Weapon(float x, float y, String img, int targetX, int targetY, int ax, int pixels, int damage)
            throws SlickException {
        this.damage = damage;
        this.isActive = true;

        this.walk = new Image[ax];
        this.ani = new Animation();

        this.gsprite = new SpriteSheet("TilePack/Projectiles/" + img + ".png", pixels, pixels);
        gsprite.startUse();
        for (int i = 0; i < ax; i++) {
            walk[i] = gsprite.getSubImage(i, 0);
        }
        gsprite.endUse();

        this.ani = new Animation(walk, 100);
        this.ani.start(); // Start the animation

        this.hitbox = new Rectangle(x, y, walk[0].getHeight(), walk[0].getWidth());

        float length = (float) Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
        if (length != 0) {
            this.deltaX = (targetX - x) / length;
            this.deltaY = (targetY - y) / length;
        }

        rotation = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
    }

    public void move() {
        if (!isActive) {
            return;
        }

        float speed = 2.0f; // Adjust speed as necessary
        hitbox.setX(hitbox.getX() + deltaX * speed);
        hitbox.setY(hitbox.getY() + deltaY * speed);

        if (isOutOfScreen(hitbox)) {
            isActive = false;
        }
    }

    private boolean isOutOfScreen(Rectangle hitbox) {
        int screenWidth = 1472; // Screen width
        int screenHeight = 830; // Screen height

        return hitbox.getX() < 0 || hitbox.getX() > screenWidth ||
                hitbox.getY() < 0 || hitbox.getY() > screenHeight;
    }

    public void draw(Graphics g) {
        if (isActive) {
            // Get the center of the hitbox for rotation
            float centerX = hitbox.getX() + hitbox.getWidth() / 2;
            float centerY = hitbox.getY() + hitbox.getHeight() / 2;

            // Save the current transformation of the Graphics context
            g.pushTransform();

            // Rotate the graphics context
            g.rotate(centerX, centerY, rotation);

            // Draw the animation
            ani.draw(hitbox.getX(), hitbox.getY());

            // Restore the original transformation of the Graphics context
            g.popTransform();
        }
    }

    public int getDamage()
    {
        return damage;
    }

    public Rectangle getHitbox()
    {
        return hitbox;
    }
}
