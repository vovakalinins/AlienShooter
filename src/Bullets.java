import org.newdawn.slick.SlickException;

public class Bullets extends Weapon {
    public Bullets(int x, int y, Player player) throws SlickException
    {
        super(player.getX(), player.getY(), "Bullets", x, y, 3,16, 25); // 100 for Max Health
    }
}
