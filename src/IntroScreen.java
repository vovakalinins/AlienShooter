import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class IntroScreen extends BasicGameState {
    
    private Image mainImg;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
       mainImg = new Image("Intro.png");
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException { 
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            //go to next screen (id = 1) when mouse is clicked - fade out to it
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
       mainImg.draw(0,0);
    }
    
    public int getID() {
       return 0;  //this id will be different for each screen
    }

    
}