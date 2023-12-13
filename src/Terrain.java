import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class Terrain {

    SpriteSheet tileset;
    static int pixels = 32;
    static int size_x = 46, size_y = 26;
    boolean[][] track_tiles = new boolean[size_x][size_y];
    private ArrayList<Rectangle> barriers = new ArrayList<Rectangle>();
    private int spawnX, spawnY;

    public Terrain() throws SlickException {
        tileset = new SpriteSheet("TilePack/Tileset/tileset_arranged.png", pixels, pixels);

        for (int x = 0; x < size_x; x++) {
            addBarrier(x, 0);
            addBarrier(x, size_y - 1);
        }
        for (int y = 0; y < size_y; y++) {
            addBarrier(0, y);
            addBarrier(size_x - 1, y);
        }

        tileset.startUse();
        tileset.endUse();
        dirt = tileset.getSprite(10, 7);
        wall_btm = tileset.getSprite(4, 2);
        wall_lft = tileset.getSprite(3, 1);

        road_intersection = new Image[] {
                tileset.getSprite(8, 5),
                tileset.getSprite(9, 5),
                tileset.getSprite(8, 6),
                tileset.getSprite(9, 6)
        };

        road_up = new Image[] {
                tileset.getSprite(8, 7),
                tileset.getSprite(9, 7),
                tileset.getSprite(8, 8),
                tileset.getSprite(9, 8)
        };

        road_side = new Image[] {
                tileset.getSprite(10, 5),
                tileset.getSprite(11, 5),
                tileset.getSprite(10, 6),
                tileset.getSprite(11, 6)
        };

        spawnhole = new Image[] {
                tileset.getSprite(9, 9),
                tileset.getSprite(10, 9),
                tileset.getSprite(9, 10),
                tileset.getSprite(10, 10)
        };
    }
    
    public void drawGrid(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        TrueTypeFont ttf = new TrueTypeFont(new java.awt.Font("Arial", 0, 10), true);
        for (int i = 0; i < pixels * 46; i += pixels) {
            for (int j = 0; j < pixels * 26; j += pixels) {
                Rectangle box = new Rectangle(i, j, pixels, pixels);
                g.draw(box);
                ttf.drawString(i + 3, j + 3, "" + (i / pixels), Color.white);
                ttf.drawString(i + 3, j + 12, "" + (j / pixels), Color.white);
            }
        }
        g.setColor(Color.red);
        for (Rectangle rect : barriers)
        {
            g.draw(rect);
        }
    }

    Image grass;
    Image dirt;
    Image wall_btm, wall_lft;
    Image[] road_intersection = new Image[4];
    Image[] road_up = new Image[4];
    Image[] road_side = new Image[4];  
    Image[] spawnhole = new Image[4];  
    Random random = new Random();

    public void draw() {
        // draw dirt
        drawDirt(dirt);
        drawWalls();
        drawRoad();
        //drawSpawn(12, 4);
        //
    }

    public void drawSpawn(int x, int y)
    {
        spawnX=x*32;
        spawnY=y*32;
        draw(spawnhole[0], x, y);
        draw(spawnhole[1], x + 1, y);
        draw(spawnhole[2], x, y + 1);
        draw(spawnhole[3], x + 1, y + 1);
    }

    private void drawRoad() {
        int x = 21;
        int y = 12;
        draw(road_intersection[0], x, y);
        draw(road_intersection[1], x + 1, y);
        draw(road_intersection[2], x, y + 1);
        draw(road_intersection[3], x + 1, y + 1);

        for (int y1 = 1; y1 < y-1; y1++) {
            draw(road_up[0], x, y1);
            draw(road_up[1], x + 1, y1);
            draw(road_up[2], x, y1 + 1);
            draw(road_up[3], x + 1, y1 + 1);
        }
        for (int y1 = 14; y1 < size_y-2; y1++) {
            draw(road_up[0], x, y1);
            draw(road_up[1], x + 1, y1);
            draw(road_up[2], x, y1 + 1);
            draw(road_up[3], x + 1, y1 + 1);
        }

        for (int x1 = 1; x1 < x-1; x1++) {
            draw(road_side[0], x1, y);
            draw(road_side[1], x1 + 1, y);
            draw(road_side[2], x1, y + 1);
            draw(road_side[3], x1 + 1, y + 1);
        }
        for (int x1 = 23; x1 < size_x-2; x1++) {
            draw(road_side[0], x1, y);
            draw(road_side[1], x1 + 1, y);
            draw(road_side[2], x1, y + 1);
            draw(road_side[3], x1 + 1, y + 1);
        }
    }

    private void drawWalls() {
        for (int x = 0; x < size_x; x++) {
            draw(wall_btm, x, 0);
            draw(wall_btm, x, size_y - 1);
        }
        for (int y = 0; y < size_y; y++) {
            draw(wall_lft, 0, y);
            draw(wall_lft, size_x - 1, y);
        }
    }

    private void drawDirt(Image dirt) {
        for (int x = 0; x < size_x; x++) {
            for (int y = 0; y < size_y; y++) {
                draw(dirt, x, y);
            }
        }
    }

    private void draw(Image i, int x, int y) {
        i.draw(x * pixels, y * pixels);
    }

    public int convert(int i) {
        return i * pixels;
    }

    private void addBarrier(int x, int y) {
        Rectangle newBarrier = new Rectangle(x * pixels, y * pixels, pixels, pixels);
        if (!barriers.contains(newBarrier)) {
            barriers.add(newBarrier);
        }
    }

    public Image getImg(int x, int y) {
        return tileset.getSprite(x, y);
    }

    public boolean canPlace(int mouseX, int mouseY) {
        return !track_tiles[mouseX][mouseY];
    }

    public ArrayList<Rectangle> getBarriers()
    {
        return barriers;
    }

    public int getWidth()
    {
        return size_x*pixels;
    }

    public int getHeight()
    {
        return size_y*pixels;
    }

    public int getSpawnX()
    {
        return spawnX;
    }
    
    public int getSpawnY()
    {
        return spawnY;
    }
}
