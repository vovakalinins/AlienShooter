
import org.newdawn.slick.*;

public class showIDS extends BasicGame {

    SpriteSheet tileset;
    static int pixels = 32;
    float scale = 1.75f; // Scaling factor, set to 2.0 for double size, adjust as needed

    public showIDS(String title) {
        super(title);
    }

    public void init(GameContainer gc) throws SlickException {
        tileset = new SpriteSheet("TilePack/Tileset/tileset_arranged.png", pixels, pixels);
    }

    public void update(GameContainer gc, int i) throws SlickException {

    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Get the number of horizontal and vertical tiles
        int horizontalTiles = tileset.getWidth() / pixels;
        int verticalTiles = tileset.getHeight() / pixels;

        // Loop through each tile in the sprite sheet
        for(int x = 0; x < horizontalTiles; x++) {
            for(int y = 0; y < verticalTiles; y++) {
                // Calculate scaled positions and sizes
                int scaledX = (int)(x * pixels * scale);
                int scaledY = (int)(y * pixels * scale);
                int scaledWidth = (int)(pixels * scale);
                int scaledHeight = (int)(pixels * scale);

                // Draw the tile with scaling
                g.drawImage(tileset.getSubImage(x, y).getScaledCopy(scaledWidth, scaledHeight), scaledX, scaledY);

                // Draw the tile ID
                String id = String.format("(%d,%d)", x, y);
                // Calculate the position for the text, adjust if necessary
                int textX = scaledX; 
                int textY = scaledY;
                g.drawString(id, textX, textY);
            }
        }
    }


    public static void main(String args[]) throws SlickException {
        showIDS game = new showIDS("Testing Game");
        AppGameContainer app = new AppGameContainer(game);
        app.setDisplayMode(1472, 832, false); // SET TO DIMENSIONS 
        app.setShowFPS(false);
        app.setTargetFrameRate(100);
        app.start();
    }

}
