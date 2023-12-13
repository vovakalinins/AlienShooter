import org.newdawn.slick.SlickException;

public class RPG extends Weapon {
    public RPG(int x, int y, Player player) throws SlickException
    {
        super(player.getX(), player.getY(), "RPG", x, y, 3,32, 100); // 100 for Max Health
    }
}
