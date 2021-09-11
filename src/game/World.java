/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 *         Jiayu Li
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import gameData.GameData;
import gameData.ItemData;
import gameData.KartData;

// Represents the entire game world.
// (instantiated just once for the whole game)
public class World {
	
	private GameMap map;

    // The player's kart
    private Player player;
    
    // The enemies
    private Elephant elephant;
    private Dog dog;
    private Octopus octopus;
    
    // The camera used to track what the screen should show
    private Camera camera;
    
    // All items or hazards in the game
    private Item[] items;
    
    // The panel
    private Panel panel;

    // Create a new World object
    public World() throws SlickException {
    	// Create map
        map = new GameMap();
        // Create karts
        player = new Player();
        elephant = new Elephant();
        dog = new Dog();
        octopus = new Octopus();
        // Create items
        items = new Item[ItemData.ITEMS];
        for (int i = 0; i < ItemData.ITEMS; i++) {
        	String imgPath = ItemData.getItemImgPath(ItemData.getItem(i)[ItemData.ITEM_ID]);
        	int[] itemData = ItemData.getItem(i);
        	items[i] = new Item(i, itemData[ItemData.ITEM_X], itemData[ItemData.ITEM_Y], itemData[ItemData.ITEM_ID], imgPath);
        }
        
        camera = new Camera(GameData.SCREENWIDTH, GameData.SCREENHEIGHT, this.player);
        panel = new Panel();
    }
    
    public double frictionAt(int x, int y) {
    	return map.frictionAt(x, y);
    }
    
    public boolean blockingAt(int x, int y) {
    	return map.blockingAt(x, y);
    }
    
    public Elephant getElephant() {
    	return elephant;
    }
    
    public Dog getDog() {
    	return dog;
    }
    
    public Octopus getOctopus() {
    	return octopus;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    // Return true of player has reached the goal
    private boolean isGoal() {
    	return player.getY() < GameData.GOAL;
    }
    
    // Find the ranking of the player.
    private int getPlayerRanking(Player player, Elephant elephant, Dog dog, Octopus octopus) {
    	double playerY = player.getY();
    	int ranking = KartData.NUM_KARTS;
    	if (playerY < elephant.getY()) ranking--;
    	if (playerY < dog.getY()) ranking--;
    	if (playerY < octopus.getY()) ranking--;
    	return ranking;
    }
    
    // ====================================================================================
    // = Updates
    // ====================================================================================
    
    // Update the game state for a frame.
    // rotate_dir: The player's direction of rotation. -1 for anti-clockwise, 1 for clockwise, or 0
    // move_dir: The player's movement in the kart's axis (-1, 0 or 1).
    // use_item: boolean value, if true, use the item, false otherwise.
    public void update(double rotateDir, double moveDir, boolean useItem) throws SlickException {
        // Set rotate_dir and move_dir to 0 if the player reached the goal already. 
    	if (player.getY() < GameData.GOAL) {
    		rotateDir = 0;
    		moveDir = 0;
    		useItem = false;
    	}
    	updateKarts(rotateDir, moveDir);
    	updateItems(useItem);
        camera.follow(player);
    }
    
    // Update all karts
    private void updateKarts(double rotateDir, double moveDir) {
    	// Rotate and move the player by rotate_dir and move_dir
    	player.update(rotateDir, moveDir, this);
        elephant.update(this);
        dog.update(this);
        octopus.update(this);
    }
    
    // Update all items
    private void updateItems(boolean useItem) throws SlickException {
    	for (int i = 0; i < ItemData.ITEMS; i++) {
        	// Use the item if use_item is true and player is holding this item. 
        	if (useItem && player.getItem() != null && i==player.getItem().getItemIndex())
        		items[i].useItem(this, player);
        	// If this item exists, use different update method depending on the item.
        	if (items[i].getExist()) {
        		if (items[i].getItemId() == ItemData.TOMATO_PROJ)
        			items[i].updateTomato(this);
        		else
        			items[i].update(this);
        	}
        }
    }
    
    // ====================================================================================
    // = Rendering
    // ====================================================================================

    // Render the entire screen, so it reflects the current game state.
    public void render(Graphics g) throws SlickException {
    	// Render the map
    	map.renderMap(camera.getLeft(), camera.getTop(), camera.getWidth(), camera.getHeight());
    	// Render the karts
        player.render(g, camera);
        elephant.render(g, camera);
        dog.render(g, camera);
        octopus.render(g, camera);
        // Render the items
        for(int i = 0; i < ItemData.ITEMS; i++) {
        	items[i].render(g, camera);
        }
        // Find the current ranking of the player and display on the panel. 
        int ranking = getPlayerRanking(player, elephant, dog, octopus);
        panel.render(g, ranking, player.getItem());
        // Find the final ranking of the player and display the message when game finishes. 
        if (isGoal()) {
        	String finalRanking = Panel.ordinal(ranking);
        	g.drawString("You are the "+ finalRanking +"!", GameData.MESSAGE_X, GameData.MESSAGE_Y);
        }
    }
    
    
    
}
