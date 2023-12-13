import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Player {
    private int maxHealth;
    private int health;

    private Animation ani[] = new Animation[2];
    private Image walk[][] = new Image[2][2];
    private Image stopImage[] = new Image[2];
    private SpriteSheet gsprite;
    private boolean stopped;
    private Rectangle hitbox;
    private int dir;

    public Player(int x, int y, String _class) throws SlickException {
        gsprite = new SpriteSheet("TilePack/Soldiers/" + _class + "-Class.png", 32, 32);
        gsprite.startUse();
        for (int i = 0; i < 2; i++) {
            stopImage[i] = gsprite.getSubImage(i, 0);
            for (int j = 0; j < 2; j++) {
                walk[i][j] = gsprite.getSubImage(i, j);
            }
            ani[i] = new Animation(walk[i], 100);
        }
        gsprite.endUse();
        hitbox = new Rectangle(x, y, 32, 32);
        stopped = true;
        dir = 1;
        maxHealth = 100;
        health = maxHealth;
    }

    public void move(Input kb, ArrayList<Rectangle> walls) {
        stopped = false;
        int x = (int) hitbox.getX();
        int y = (int) hitbox.getY();
        int originx = x, originy = y;

        if (kb.isKeyDown(Input.KEY_RIGHT) || kb.isKeyDown(Input.KEY_D) ) {
            x++;
            dir = 1;
        } else if (kb.isKeyDown(Input.KEY_LEFT) || kb.isKeyDown(Input.KEY_A)) {
            x--;
            dir = 0;
        } else if (kb.isKeyDown(Input.KEY_DOWN) || kb.isKeyDown(Input.KEY_S)) {
            y++;
        } else if (kb.isKeyDown(Input.KEY_UP) || kb.isKeyDown(Input.KEY_W)) {
            y--;
        } else {
            stopped = true;
        }
        hitbox.setX(x);
        hitbox.setY(y);

        if (isHitting(walls)) {
            hitbox.setX(originx);
            hitbox.setY(originy);
        }
    }

    public boolean isHitting(ArrayList<Rectangle> rect) {
        for (Rectangle r : rect) {
            if (hitbox.intersects(r)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        if (stopped) {
            ani[1].stop();
            if (dir == 1) {
                stopImage[1].draw(hitbox.getX(), hitbox.getY());
            } else {
                stopImage[1].draw(hitbox.getX() + 32, hitbox.getY(), -1 * stopImage[1].getWidth(),
                        stopImage[1].getHeight());
            }
        } else {
            ani[1].start();
            if (dir == 1) {
                ani[1].draw(hitbox.getX(), hitbox.getY());
            } else {
                ani[1].draw(hitbox.getX() + 32, hitbox.getY(), -1 * ani[1].getWidth(), ani[1].getHeight());
            }
        }

        drawHealthBar(g);

        // g.draw(hitbox);
    }

    private void drawHealthBar(Graphics g) {
        int healthBarWidth = 30; // Width of the health bar
        int healthBarHeight = 5; // Height of the health bar

        // Calculate health bar length based on current health
        float healthBarLength = (float) health / maxHealth * healthBarWidth;

        // Draw the background of the health bar (empty part)
        g.setColor(Color.red);
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarWidth, healthBarHeight);

        // Draw the foreground of the health bar (filled part)
        g.setColor(Color.green);
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarLength, healthBarHeight);
    }

    public float getX() {
        return hitbox.getX();
    }

    public float getY() {
        return hitbox.getY();
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    // Method to restore health
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    // Getters for health
    public int getCurrentHealth() {
        return health;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
