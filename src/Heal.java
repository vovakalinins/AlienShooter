import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Heal {
    Image texture;
    protected Rectangle hitbox;
    int x,y;
    private boolean show = false;

    public Heal(int x, int y) throws SlickException
    {
        texture = new Image("TilePack/Medicine.png");
        this.x=x;
        this.y=y;
        hitbox = new Rectangle(x, y, texture.getHeight(), texture.getWidth());
        show=true;
    }

    public void draw()
    {
        if (show) texture.draw(x,y);
    }

    public Rectangle getHitbox()
    {
        return hitbox;
    }

    public void healed()
    {
        hitbox.setX(10000);
        show=false;
    }
}
