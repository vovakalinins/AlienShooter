import org.newdawn.slick.SlickException;

public class Grenades extends Weapon {
    public Grenades(int x, int y, Player player) throws SlickException
    {
        super(player.getX(), player.getY(), "Grenade", x, y, 8,16, 100); // 100 for Max Health
    }
}
