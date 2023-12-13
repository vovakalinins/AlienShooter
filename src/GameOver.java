import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState{
    static String message = "bru";
    static int msgx=100;
    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        
    }

    public static void setMessage(String msg, int x)
    {
        message=msg;
        msgx=x;
    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
        arg2.setColor(Color.green);
        arg2.drawString("GAME OVER", arg0.getWidth()/2, (arg0.getHeight()/2)-100);
    }

    public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

    }

    public int getID() {
        return 2;
    }
    
}
