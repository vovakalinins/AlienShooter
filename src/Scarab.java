import org.newdawn.slick.SlickException;

public class Scarab extends Enemy {

    public Scarab(int x, int y, Player target) throws SlickException
    {
        super(x, y, "Scarab", target, 250,4,4, 32,10); // 100 for Max Health
    }
}
