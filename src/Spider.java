import org.newdawn.slick.SlickException;

public class Spider extends Enemy {

    public Spider(int x, int y, Player target) throws SlickException
    {
        super(x, y, "Spider", target, 500,4,4, 32,10); // 100 for Max Health
    }
}
