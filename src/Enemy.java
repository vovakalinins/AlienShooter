import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Enemy {
    private int maxHealth;
    private int health;
    private int damage;

    protected Rectangle hitbox;

    private SpriteSheet gsprite;
    private Image walk[][];
    private Animation ani[];

    private Player target;
    float speed;
    private Random rand = new Random();

    public Enemy(int x, int y, String enemy, Player target, int health, int ax, int ay, int pixels, int damage) throws SlickException {
        walk = new Image[ax][ay];
        ani = new Animation[ax];

        gsprite = new SpriteSheet("TilePack/Robots/" + enemy + ".png", pixels, pixels);
        gsprite.startUse();
        for (int i = 0; i < ax; i++) {
            for (int j = 0; j < ay; j++) {
                walk[i][j] = gsprite.getSubImage(i, j);
            }
            ani[i] = new Animation(walk[i], 100);
        }
        gsprite.endUse();
        hitbox = new Rectangle(x, y, walk[0][0].getHeight(), walk[0][0].getWidth());
        this.damage=damage;
        this.target = target;
        this.health = health;
        this.maxHealth=health;
        speed = (0.75f * rand.nextFloat()) + 0.23f;
    }

    public void move(ArrayList<Rectangle> barriers) {
        float enemyX = hitbox.getX();
        float enemyY = hitbox.getY();

        float playerX = target.getX();
        float playerY = target.getY();

         // Adjust speed as necessary

        // Calculate direction towards the player
        float deltaX = playerX - enemyX;
        float deltaY = playerY - enemyY;

        // Normalize the direction
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length != 0) {
            deltaX /= length;
            deltaY /= length;
        }

        // Update enemy's position
        hitbox.setX(enemyX + deltaX * speed);
        hitbox.setY(enemyY + deltaY * speed);
    }

    public void draw(Graphics g) {
        ani[1].start();
        ani[1].draw(hitbox.getX(), hitbox.getY());
        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int healthBarWidth = 30; // Width of the health bar
        int healthBarHeight = 5; // Height of the health bar

        // Calculate health bar length based on current health
        float healthBarLength = (float) health / maxHealth * healthBarWidth;

        // Draw the background of the health bar (empty part)
        g.setColor(Color.gray);
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarWidth, healthBarHeight);

        // Draw the foreground of the health bar (filled part)
        g.setColor(Color.green);
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarLength, healthBarHeight);
    }

    public int getStrength()
    {
        return damage;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive()
    {
        if (health>0) return true;
        return false; 
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
