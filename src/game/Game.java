/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 *         Jiayu Li
 */

package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import gameData.GameData;

// Main class for the Shadow Kart Game engine. 
// Handles initialisation, input and rendering
public class Game extends BasicGame {
    
    // The game state
    private World world;

    // Create a new Game object
    public Game() {
        super("Shadow Kart");
    }

    // Initialise the game state
    @Override
    public void init(GameContainer gc) throws SlickException {
        world = new World();
    }

    // Update the game state for a frame.
    // delta is the time passed since last frame in milliseconds.
    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // Update the player's rotation and position based on key presses.
        double rotateDir = 0;
        double moveDir = 0;
        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            moveDir -= 1;
        }
        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            moveDir += 1;
        }
        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            rotateDir -= 1;
        }
        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            rotateDir += 1;
        }
        boolean useItem = input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL);

        // Let World.update decide what to do with this data.
        for (int i=0; i<delta; i++) {
            world.update(rotateDir, moveDir, useItem);
        }
    }

    // Render the entire screen so it reflects the current game state.
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        // Let World.render handle the rendering.
        world.render(g);
    }

    // Start-up method. Creates the game and runs it.
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        app.setShowFPS(false); // setShowFPS(true) to show frames-per-second.
        app.setDisplayMode(GameData.SCREENWIDTH, GameData.SCREENHEIGHT, false);
        app.start();
    }
}
