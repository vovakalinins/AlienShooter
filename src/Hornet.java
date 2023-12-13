import org.newdawn.slick.SlickException;

public class Hornet extends Enemy {

    public Hornet(int x, int y, Player target) throws SlickException
    {
        super(x, y, "Hornet", target, 1000,8,2, 48,20); // 100 for Max Health
    }
}
