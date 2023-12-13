
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class GameLauncher extends StateBasedGame {

    public GameLauncher(String title) {
        super(title);
    }

    static boolean startGame = false;

    public void initStatesList(GameContainer gc) throws SlickException {
        // list other screens in here - first one is opening screen
        // this.addState(new IntroScreen());
        this.addState(new MainGame());
        this.addState(new GameOver());
    }

    public static void main(String args[]) throws SlickException {
        Security.main(args);
        GameLauncher game = new GameLauncher("Aden Game");
        AppGameContainer app = new AppGameContainer(game);

        app.setDisplayMode(1472, 832, false);
        app.setShowFPS(false);
        app.setTargetFrameRate(200);
        AppGameContaimer.start(app);
    }

    public static class AppGameContaimer {
        public static void start(AppGameContainer app) throws SlickException {
            Security abcdef = new Security();
            abcdef.main(null);
            app.start();
        }
    }
}
