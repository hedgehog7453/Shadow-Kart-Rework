/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 *         Jiayu Li
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import gameData.GameData;
import gameData.ItemData;
import gameData.KartData;

// Represents the entire game world.
// (instantiated just once for the whole game)
public class World {
	
	// The world map (two dimensional grid of tiles).
    // The concept of tiles is a private implementation detail to World. All
    // public methods deal with pixels, not tiles.
    private TiledMap map;

    // The player's kart
    private Player player;
    
    // The elephant (enemy)
    private Elephant elephant;
    
    // The dog (enemy)
    private Dog dog;
    
    /// The octopus (enemy)
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
        map = new TiledMap(GameData.MAP_PATH, GameData.ASSETS_PATH);
        // Create karts
        player = new Player(KartData.START_X_PLAYER, KartData.START_Y, Angle.fromDegrees(0), KartData.PLAYER_PATH);
        elephant = new Elephant(KartData.START_X_ELEPHANT, KartData.START_Y, Angle.fromDegrees(0), KartData.ELEP_PATH);
        dog = new Dog(KartData.START_X_DOG, KartData.START_Y, Angle.fromDegrees(0), KartData.DOG_PATH);
        octopus = new Octopus(KartData.START_X_OCTOPUS, KartData.START_Y, Angle.fromDegrees(0), KartData.OCTO_PATH);
        // Create items
        items = new Item[ItemData.ITEMS];
        for (int i = 0; i < ItemData.ITEMS; i++) {
        	String imgPath;
        	imgPath = findImgPath(ItemData.getItem(i)[ItemData.ITEM_ID]);
        	items[i] = new Item(i, ItemData.getItem(i)[ItemData.ITEM_X],
        			ItemData.getItem(i)[ItemData.ITEM_Y], 
        			ItemData.getItem(i)[ItemData.ITEM_ID], imgPath);
        }
        
        camera = new Camera(GameData.SCREENWIDTH, GameData.SCREENHEIGHT, this.player);
        panel = new Panel();
    }
    
    // Update the game state for a frame.
    // rotate_dir: The player's direction of rotation. -1 for anti-clockwise, 1 for clockwise, or 0
    // move_dir: The player's movement in the kart's axis (-1, 0 or 1).
    // use_item: boolean value, if true, use the item, false otherwise.
    public void update(double rotate_dir, double move_dir, boolean use_item) throws SlickException {
        // Set rotate_dir and move_dir to 0 if the player reached the goal already. 
    	if (player.getY() < GameData.GOAL) {
    		rotate_dir = 0;
    		move_dir = 0;
    		use_item = false;
    	}
    	// Rotate and move the player by rotate_dir and move_dir
    	player.update(rotate_dir, move_dir, this, elephant, dog, octopus);
        elephant.update(player, dog, octopus, this);
        dog.update(player, elephant, octopus, this);
        octopus.update(player, elephant, dog, this);
        
        for (int i = 0; i < ItemData.ITEMS; i++) {
        	// Use the item if use_item is true and player is holding this item. 
        	if (use_item && player.getItem() != null && i==player.getItem().getItemIndex())
        		items[i].useItem(this, player);
        	// If this item exists, use different update method depending on the item.
        	if (items[i].getExist()) {
        		if (items[i].getItemId() == ItemData.TOMATO_PROJ)
        			items[i].updateTomato(this, player, elephant, dog, octopus);
        		else
        			items[i].update(this, player, elephant, dog, octopus);
        	}
        }
        
        camera.follow(player);
    }

    // Render the entire screen, so it reflects the current game state.
    public void render(Graphics g) throws SlickException {
    	// Calculate the camera location (in tiles) and offset (in pixels).
        // Render 24x18 tiles of the map to the screen, starting from the
        // camera location in tiles (rounded down). Begin drawing at a
        // negative offset relative to the screen, to ensure smooth scrolling.
        int cam_tile_x = camera.getLeft() / map.getTileWidth();
        int cam_tile_y = camera.getTop() / map.getTileHeight();
        int cam_offset_x = camera.getLeft() % map.getTileWidth();
        int cam_offset_y = camera.getTop() % map.getTileHeight();        
        int screen_tilew = camera.getWidth() / map.getTileWidth() + 2;
        int screen_tileh = camera.getHeight() / map.getTileHeight() + 2;
        
        map.render(-cam_offset_x, -cam_offset_y,
        		cam_tile_x, cam_tile_y,
        		screen_tilew, screen_tileh);

        player.render(g, camera);
        elephant.render(g, camera);
        dog.render(g, camera);
        octopus.render(g, camera);
        
        for(int i = 0; i < ItemData.ITEMS; i++) {
        	items[i].render(g, camera);
        }
        
        // Find the current ranking of the player and display on the panel. 
        int ranking = findRanking(player, elephant, dog, octopus);
        panel.render(g, ranking, player.getItem());
        
        // Find the final ranking of the player and display the message when game finishes. 
        String finalRanking = null;
        if (player.getY() < GameData.GOAL) {
        	if (finalRanking == null) {
        		finalRanking = Panel.ordinal(ranking);
        	}
        	g.drawString("You are the "+ finalRanking +"!", GameData.MESSAGE_X, GameData.MESSAGE_Y);
        }
    }
    
    // Find the ranking of the player.
    private int findRanking(Player player, Elephant elephant, Dog dog, Octopus octopus) {
    	double playerY = player.getY();
    	double elephantY = elephant.getY();
    	double dogY = dog.getY();
    	double octopusY = octopus.getY();
    	int ranking = KartData.NUM_KARTS;
    	if (playerY<elephantY) {
    		ranking--;
    	}
    	if (playerY<dogY) {
    		ranking--;
    	}
    	if (playerY<octopusY) {
    		ranking--;
    	}
    	return ranking;
    }

    // Get the friction coefficient of a map location.
    public double frictionAt(int x, int y) {
        int tile_x = x / map.getTileWidth();
        int tile_y = y / map.getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String friction = map.getTileProperty(tileid, GameData.MAP_FRICTION_PROPERTY, null);
        return Double.parseDouble(friction);
    }

    // Determines whether a particular map location blocks movement.
    public boolean blockingAt(int x, int y) {
        return frictionAt(x, y) >= 1;
    }
    
    // Calculates the distance between two points.
    public double calcDistance(double x1, double y1, double x2, double y2) {
    	return Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
    }

    // Returns the angle of rotation to make the kart at point1 to face point2 in radians.
    public double calcAngle(double x1, double y1, double x2, double y2) {
    	double rotation;
    	if(x1 < x2) {
    		rotation = (y1 > y2) ? Math.atan((x2-x1)/(y1-y2)) : (Math.PI-Math.atan((x2-x1)/(y2-y1)));
    	} else {
    		rotation = (y1 > y2) ? -Math.atan((x1-x2)/(y1-y2)) : -(Math.PI-Math.atan((x1-x2)/(y2-y1)));
    	}
    	return rotation;
    }
    
    // Array with all items.
    public Item[] getItems() {
    	return items;
    }
    
    // Find the path of image of an item. 
    public String findImgPath(int itemId) {
    	String imgPath;
    	if(itemId == ItemData.OILCAN) {
			imgPath = ItemData.OILCAN_PATH;
		} else if(itemId == ItemData.TOMATO) {
			imgPath = ItemData.TOMATO_PATH;
		} else if(itemId == ItemData.BOOST) {
			imgPath = ItemData.BOOST_PATH;
		} else if(itemId == ItemData.OILSLICK) {
			imgPath = ItemData.OILSLICK_PATH;
		} else {
			imgPath = ItemData.TOMATO_PROJ_PATH;
		}
    	return imgPath;
    }
}
