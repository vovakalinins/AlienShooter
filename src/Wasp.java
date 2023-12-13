import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Wasp extends Enemy {

    public Wasp(int x, int y, Player target) throws SlickException {
        super(x, y, "Wasp", target, 200, 8, 1, 32, 10); // 100 for Max Health
    }

}
