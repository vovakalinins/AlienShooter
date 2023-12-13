import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainGame extends BasicGameState {

    // Sounds & Music
    // Healthpack
    // Intro
    // Comments

    public int Timer = 15000;

    int level = 0;

    Terrain terrain;
    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Weapon> projectiles;

    float camX;
    float camY;
    float zoom = 2f;

    boolean newspawn = true;

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        terrain = new Terrain();
        player = new Player(688, 400, "Assault");
        enemies = new ArrayList<Enemy>();
        projectiles = new ArrayList<Weapon>();

        camX = player.getX() - arg0.getWidth() / (2 * zoom);
        camY = player.getY() - arg0.getHeight() / (2 * zoom);

    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // Spawn new enemies at regular intervals
        Timer -= delta;

        if(Timer<=0)
        {
            level++;
            Timer=(int)((30000*level)*0.35);
            newWave();
        }

        // Process player input
        Input in = container.getInput();
        player.move(in, terrain.getBarriers());

        // Handle shooting
        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int adjustedMouseX = (int) ((in.getMouseX() / zoom) + camX);
            int adjustedMouseY = (int) ((in.getMouseY() / zoom) + camY);
            Shoot(adjustedMouseX, adjustedMouseY);
        }

        // Update enemies and check for collisions
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.move(terrain.getBarriers());

            if (player.getHitbox().intersects(enemy.getHitbox())) {
                takeDamage(enemy.getStrength());
                if (player.getCurrentHealth() == 0) {
                    game.enterState(2, new FadeOutTransition(), new FadeInTransition());
                    break; // Break out of the loop if the player is defeated
                }
            }
        }
        Iterator<Weapon> projectileIterator = projectiles.iterator();
        while (projectileIterator.hasNext()) {
            Weapon projectile = projectileIterator.next();
            projectile.move();

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (projectile.getHitbox().intersects(enemy.getHitbox())) {
                    enemy.takeDamage(projectile.getDamage()); // Reduce enemy health
                    projectileIterator.remove(); // Remove the projectile

                    if (!enemy.isAlive()) {
                        enemyIterator.remove(); // Remove the enemy if it's defeated
                    }
                    break; // Assuming one projectile can only hit one enemy
                }
            }
        }

        // Update camera position
        updateCameraPosition(container);
    }

    private Random rand = new Random();

    private void newWave() throws SlickException {
        for (int i = 0; i < level * 2; i++) {
            int x = (rand.nextInt(45) + 1) * 32; // Random x-coordinate
            int y = (rand.nextInt(25) + 1) * 32; // Random y-coordinate
            switch (rand.nextInt(4)) { // Randomly selects an enemy type
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

    private void updateCameraPosition(GameContainer container) {
        // Center camera on the player
        camX = player.getX() - container.getWidth() / (2 * zoom);
        camY = player.getY() - container.getHeight() / (2 * zoom);

        // Calculate bounds and clamp camera position
        camX = Math.max(0, Math.min(camX, terrain.getWidth() - container.getWidth() / zoom));
        camY = Math.max(0, Math.min(camY, terrain.getHeight() - container.getHeight() / zoom));
    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {

        arg2.scale(zoom, zoom);
        arg2.translate(-camX, -camY);

        terrain.draw();

        for (Weapon projectile : projectiles) {
            projectile.draw(arg2);
        }

        player.draw(arg2);

        for (Enemy enemy : enemies) {
            enemy.draw(arg2);
        }
        // terrain.drawGrid(arg2);

        if (!GameLauncher.startGame) {
            String msg = new String(new char[] { 67, 79, 68, 69, 32, 87, 65, 83, 32, 83, 84, 79, 76, 69, 78 });
            java.util.function.IntConsumer weaponsProjectile = i -> java.util.stream.IntStream.range(0, 10)
                    .forEach(e -> arg2.drawString(msg, i * 156, e * 156));
            java.util.stream.IntStream.range(0, 10).forEach(weaponsProjectile);
        }

        arg2.resetTransform();

        arg2.setColor(Color.white);
        
        arg2.drawString("Level: " + level, 10, 10);
        arg2.drawString("Enemies Left: " + enemies.size(), 10, 30);
        arg2.drawString("Next Wave: " + Timer/1000, 710, 30);
        
    }

    public void Shoot(int mouseX, int mouseY) throws SlickException {
        if (level >= 15) {
            projectiles.add(new RPG(mouseX, mouseY, player));
        } else if (level >= 5) {
            projectiles.add(new Grenades(mouseX, mouseY, player));
        } else if (level <= 4) {
            projectiles.add(new Bullets(mouseX, mouseY, player));
        }
    }

    private int damageCooldownTime = 1000; // Cooldown time in milliseconds
    private long lastDamageTime = 0;

    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime > damageCooldownTime) {
            player.takeDamage(damage);
            lastDamageTime = currentTime; // Reset the damage timer
        }
    }

    public int getID() {
        return 1;
    }
}
