import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainGame extends BasicGameState {

    // TO DO
    // INTRO SCREEN
    // Sounds & BG Music
    //

    // here we define some stuff like timer and level
    public int Timer = 5000; // this is for the game timer
    int level = 0; // this keeps track of the game level

    // These are game objects
    Terrain terrain; // the game terrain
    Player player; // our main player
    ArrayList<Enemy> enemies; // list of enemies
    ArrayList<Weapon> projectiles; // bullets or whatever
    Heal heal;

    // Camera stuff
    float camX; // camera X position
    float camY; // camera Y position
    float zoom = 2f; // zoom level of the camera

    boolean newspawn = true; // check for new spawn

    Sound newwave, enemy, shoot, die, hurt, health;
    Music bgmusic = null;

    // Initialize game state
    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        // setup terrain, player, enemies, and stuff
        terrain = new Terrain();
        player = new Player(688, 400, "Assault");
        enemies = new ArrayList<Enemy>();
        projectiles = new ArrayList<Weapon>();

        bgmusic = new Music("backgroundmusic.aiff");
        bgmusic.loop(1, 0.10f);

        newwave = new Sound("newwave.wav");
        enemy = new Sound("enemy1.wav");
        shoot = new Sound("shoot.wav");
        die = new Sound("die.wav");
        hurt = new Sound("hurt.wav");
        health = new Sound("heal.wav");

        newHeal();
        // setting up camera position based on player
        camX = player.getX() - arg0.getWidth() / (2 * zoom);
        camY = player.getY() - arg0.getHeight() / (2 * zoom);
    }

    // Regular game update method
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // We do things every frame here, like moving stuff, checking collisions

        // Decrease timer and increase level
        Timer -= delta;
        if (Timer <= 0) {
            level++;
            Timer = (int) ((30000 * level) * 0.35);
            newWave(); // Spawn new wave of enemies
            newHeal();
        }

        // Process player input for movement and shooting
        Input in = container.getInput();
        player.move(in, terrain.getBarriers());

        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int adjustedMouseX = (int) ((in.getMouseX() / zoom) + camX);
            int adjustedMouseY = (int) ((in.getMouseY() / zoom) + camY);
            Shoot(adjustedMouseX, adjustedMouseY); // Player shooting method
        }

        // Update enemies and check collisions with player
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.move(terrain.getBarriers());

            if (player.getHitbox().intersects(enemy.getHitbox())) {
                takeDamage(enemy.getStrength());
                if (player.getCurrentHealth() == 0) {
                    game.enterState(2, new FadeOutTransition(), new FadeInTransition());
                    die.play();
                    break; // end loop if player is dead
                }
            }
        }

        if (player.getHitbox().intersects(heal.getHitbox())) {
            health.play();
            heal.healed();
            player.heal(100 - player.getCurrentHealth());
        }

        // Update projectiles and check collisions with enemies
        Iterator<Weapon> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Weapon projectile = projectileIterator.next();
            projectile.move();

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (projectile.getHitbox().intersects(enemy.getHitbox())) {
                    enemy.takeDamage(projectile.getDamage()); // enemy gets hit
                    projectileIterator.remove(); // remove the bullet

                    if (!enemy.isAlive()) {
                        die.play();
                        enemyIterator.remove(); // remove dead enemy
                    }
                    break; // one bullet one hit
                }
            }
        }

        // Update the camera to follow the player
        updateCameraPosition(container);
    }

    private Random rand = new Random();

    // Method to generate a new wave of enemies
    private void newWave() throws SlickException {
        newwave.play();
        for (int i = 0; i < level * 2; i++) {
            int x = (rand.nextInt(45) + 1) * 32; // Random x-pos for enemy
            int y = (rand.nextInt(25) + 1) * 32; // Random y-pos for enemy
            switch (rand.nextInt(4)) { // Random enemy type
                case 0:
                    enemies.add(new Scarab(x, y, player));
                    break;
                case 1:
                    enemies.add(new Wasp(x, y, player));
                    break;
                case 2:
                    enemies.add(new Spider(x, y, player));
                    break;
                case 3:
                    enemies.add(new Hornet(x, y, player));
                    break;
            }
        }
    }

    private void newHeal() throws SlickException {
        int x = (rand.nextInt(44) + 1) * 32; // Random x-pos for heal
        int y = (rand.nextInt(24) + 1) * 32; // Random y-pos for heal
        System.out.println(x + " " + y);
        heal = new Heal(x, y);
    }

    // Update the camera based on player position
    private void updateCameraPosition(GameContainer container) {
        camX = player.getX() - container.getWidth() / (2 * zoom);
        camY = player.getY() - container.getHeight() / (2 * zoom);

        camX = Math.max(0, Math.min(camX, terrain.getWidth() - container.getWidth() / zoom));
        camY = Math.max(0, Math.min(camY, terrain.getHeight() - container.getHeight() / zoom));
    }

    // Render method for drawing game elements
    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
        arg2.scale(zoom, zoom);
        arg2.translate(-camX, -camY);

        terrain.draw();

        for (Weapon projectile : projectiles) {
            projectile.draw(arg2);
        }

        heal.draw();

        player.draw(arg2);

        for (Enemy enemy : enemies) {
            enemy.draw(arg2);
        }

        arg2.resetTransform();

        // Drawing some HUD elements like level and enemy count
        arg2.setColor(Color.white);

        arg2.drawString("Level: " + level, 10, 10);
        arg2.drawString("Enemies Left: " + enemies.size(), 10, 30);
        arg2.drawString("Next Wave: " + Timer / 1000, 710, 30);
    }

    // Method for shooting based on level
    public void Shoot(int mouseX, int mouseY) throws SlickException {
        if (level >= 15) {
            projectiles.add(new RPG(mouseX, mouseY, player));
        } else if (level >= 5) {
            projectiles.add(new Grenades(mouseX, mouseY, player));
        } else if (level <= 4) {
            projectiles.add(new Bullets(mouseX, mouseY, player));
        }
        shoot.play();
    }

    private int damageCooldownTime = 1000; // Time between taking damage
    private long lastDamageTime = 0;

    // Method for player to take damage
    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime > damageCooldownTime) {
            hurt.play();
            player.takeDamage(damage);
            lastDamageTime = currentTime; // reset timer
        }
    }

    // Required method to return state ID
    public int getID() {
        return 1;
    }
}
