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

    // Constructor for Enemy
    public Enemy(int x, int y, String enemy, Player target, int health, int ax, int ay, int pixels, int damage) throws SlickException {
        // Initialize images for walking animation
        walk = new Image[ax][ay];
        ani = new Animation[ax];

        // Load sprite sheet for the enemy
        gsprite = new SpriteSheet("TilePack/Robots/" + enemy + ".png", pixels, pixels);
        // Extracting images for each animation frame
        gsprite.startUse();
        for (int i = 0; i < ax; i++) {
            for (int j = 0; j < ay; j++) {
                walk[i][j] = gsprite.getSubImage(i, j);
            }
            ani[i] = new Animation(walk[i], 100); // 100 ms per frame
        }
        gsprite.endUse();

        // Setting up hitbox and other properties
        hitbox = new Rectangle(x, y, walk[0][0].getHeight(), walk[0][0].getWidth());
        this.damage = damage;
        this.target = target;
        this.health = health;
        this.maxHealth = health;
        speed = (0.75f * rand.nextFloat()) + 0.23f; // Random speed
    }

    // Method to move enemy
    public void move(ArrayList<Rectangle> barriers) {
        float enemyX = hitbox.getX();
        float enemyY = hitbox.getY();

        float playerX = target.getX();
        float playerY = target.getY();

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

    // Method to draw enemy and health bar
    public void draw(Graphics g) {
        ani[1].start(); // Start animation
        ani[1].draw(hitbox.getX(), hitbox.getY()); // Draw the animation
        drawHealthBar(g); // Draw the health bar above enemy
    }

    // Method to draw health bar
    private void drawHealthBar(Graphics g) {
        int healthBarWidth = 30; // Width of the health bar
        int healthBarHeight = 5; // Height of the health bar

        // Calculate health bar length based on current health
        float healthBarLength = (float) health / maxHealth * healthBarWidth;

        // Draw the background and foreground of the health bar
        g.setColor(Color.gray); // Background color
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarWidth, healthBarHeight);
        g.setColor(Color.green); // Foreground color
        g.fillRect(hitbox.getX(), hitbox.getY() - 2, healthBarLength, healthBarHeight);
    }

    // Method to get enemy's damage
    public int getStrength() {
        return damage;
    }

    // Method for enemy to take damage
    public void takeDamage(int damage) {
        health -= damage; // Decrease health by damage
        if (health < 0) {
            health = 0; // Health shouldn't go below zero
        }
    }

    // Check if enemy is still alive
    public boolean isAlive() {
        return health > 0;
    }

    // Getter for hitbox
    public Rectangle getHitbox() {
        return hitbox;
    }
}
