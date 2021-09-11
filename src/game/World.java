/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca <mgiuca>
 *         Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** The string identifier to use for looking up the map friction. */
    private final String MAP_FRICTION_PROPERTY = "friction";
    
    /** Number of items in this world. */
    private final int ITEMS = 12;
    
    /** Y value of the finishing line. */
    private final int GOAL = 1026;
    
    /** X coordinate of the final message. */
    private final int MESSAGE_X = 360;
    
    /** Y coordinate of the final message. */
    private final int MESSAGE_Y = 210;
    
    /** Total number of karts in the game. */
    private final int NUM_KARTS = 4;
    
    /** Y coordinate of the start position of all karts. */
    private final int START_Y = 13086;
    
    /** X coordinate of the start position of elephant. */
    private final int START_X_ELEPHANT = 1260;
    
    /** X coordinate of the start position of dog. */
    private final int START_X_DOG = 1404;
    
    /** X coordinate of the start position of octopus. */
    private final int START_X_OCTOPUS = 1476;
    
    /** X coordinate of the start position of the player. */
    private final int START_X_PLAYER = 1332;
    		
	/** The world map (two dimensional grid of tiles).
     * The concept of tiles is a private implementation detail to World. All
     * public methods deal with pixels, not tiles.
     */
    private TiledMap map;

    /** The player's kart. */
    private Player player;
    
    /** The elephant (enemy). */
    private Elephant elephant;
    
    /** The dog (enemy). */
    private Dog dog;
    
    /** The octopus (enemy). */
    private Octopus octopus;
    
    /** The camera used to track what the screen should show. */
    private Camera camera;
    
    /** All items or hazards in the game. */
    private Item[] items;
    
    /** The panel. */
    private Panel panel;
    

    /** Create a new World object. */
    public World()
    throws SlickException
    {
        map = new TiledMap(Game.ASSETS_PATH + "/map.tmx", Game.ASSETS_PATH);
        
        player = new Player(START_X_PLAYER, START_Y, Angle.fromDegrees(0), 
        		Game.ASSETS_PATH + "/karts/donkey.png");
        elephant = new Elephant(START_X_ELEPHANT, START_Y, Angle.fromDegrees(0), 
        		Game.ASSETS_PATH + "/karts/elephant.png");
        dog = new Dog(START_X_DOG, START_Y, Angle.fromDegrees(0), 
        		Game.ASSETS_PATH + "/karts/dog.png");
        octopus = new Octopus(START_X_OCTOPUS, START_Y, Angle.fromDegrees(0), 
        		Game.ASSETS_PATH + "/karts/octopus.png");
        
        items = new Item[ITEMS];
        for(int i=0; i<ITEMS; i++)
        {
        	String imgPath;
        	imgPath = findImgPath(ItemData.getItem(i)[ItemData.ITEM_ID]);
        	items[i] = new Item(i, ItemData.getItem(i)[ItemData.ITEM_X],
        			ItemData.getItem(i)[ItemData.ITEM_Y], 
        			ItemData.getItem(i)[ItemData.ITEM_ID], imgPath);
        }
        
        camera = new Camera(Game.SCREENWIDTH, Game.SCREENHEIGHT, this.player);
        panel = new Panel();
    }
    
    /** Update the game state for a frame.
     * @param rotate_dir The player's direction of rotation
     *      (-1 for anti-clockwise, 1 for clockwise, or 0).
     * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
     * @param use_item boolean value, if true, use the item, false otherwise.
     */
    public void update(double rotate_dir, double move_dir, boolean use_item)
    throws SlickException
    {
        // Set rotate_dir and move_dir to 0 if the player reached the goal already. 
    	if (player.getY()<GOAL)
    	{
    		rotate_dir = 0;
    		move_dir = 0;
    		use_item = false;
    	}
    	// Rotate and move the player by rotate_dir and move_dir
    	player.update(rotate_dir, move_dir, this, elephant, dog, octopus);
    	
        elephant.update(player, dog, octopus, this);
        dog.update(player, elephant, octopus, this);
        octopus.update(player, elephant, dog, this);
        
        for(int i=0; i<ITEMS; i++)
        {
        	// Use the item if use_item is true and player is holding this item. 
        	if (use_item && player.getItem()!=null && i==player.getItem().getItemIndex())
        		items[i].useItem(this, player);
        	// If this item exists, use different update method depending on the item.
        	if (items[i].getExist())
        	{
        		if (items[i].getItemId()==ItemData.TOMATO_PROJ)
        			items[i].updateTomato(this, player, elephant, dog, octopus);
        		else
        			items[i].update(this, player, elephant, dog, octopus);
        	}
        }
        
        camera.follow(player);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
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
        
        map.render(
        		-cam_offset_x, -cam_offset_y,
        		cam_tile_x, cam_tile_y,
        		screen_tilew, screen_tileh);

        player.render(g, camera);
        
        elephant.render(g, camera);
        dog.render(g, camera);
        octopus.render(g, camera);
        
        for(int i=0; i<ITEMS; i++)
        {
        	items[i].render(g, camera);
        }
        
        // Find the current ranking of the player and display on the panel. 
        int ranking = findRanking(player, elephant, dog, octopus);
        panel.render(g, ranking, player.getItem());
        
        // Find the final ranking of the player and display the message when game finishes. 
        String finalRanking = null;
        if (player.getY()<GOAL)
        {
        	if (finalRanking==null)
        		finalRanking = Panel.ordinal(ranking);
        	g.drawString("You came "+ finalRanking +"!", MESSAGE_X, MESSAGE_Y);
        }
    }
    
    /** Find the ranking of the player.
     * @param player The player. 
     * @param elephant The elephant. 
     * @param dog The dog.
     * @param octopus The octopus.
     * @return ranking of the player. 
     */
    private int findRanking(Player player, Elephant elephant, Dog dog, Octopus octopus)
    {
    	double playerY = player.getY();
    	double elephantY = elephant.getY();
    	double dogY = dog.getY();
    	double octopusY = octopus.getY();
    	int ranking = NUM_KARTS;
    	if (playerY<elephantY) 
    	{
    		ranking--;
    	}
    	if (playerY<dogY)
    	{
    		ranking--;
    	}
    	if (playerY<octopusY)
    	{
    		ranking--;
    	}
    	return ranking;
    }

    /** Get the friction coefficient of a map location.
     * @param x Map tile x coordinate (in pixels).
     * @param y Map tile y coordinate (in pixels).
     * @return Friction coefficient at that location.
     */
    public double frictionAt(int x, int y)
    {
        int tile_x = x / map.getTileWidth();
        int tile_y = y / map.getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String friction =
        		map.getTileProperty(tileid, MAP_FRICTION_PROPERTY, null);
        return Double.parseDouble(friction);
    }

    /** Determines whether a particular map location blocks movement.
     * @param x Map tile x coordinate (in pixels).
     * @param y Map tile y coordinate (in pixels).
     * @return true if the tile at that location blocks movement.
     */
    public boolean blockingAt(int x, int y)
    {
        return frictionAt(x, y) >= 1;
    }
    
    /** Calculates the distance between two points.
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return distance between the two points.
     */
    public double calcDistance(double x1, double y1, double x2, double y2)
    {
    	return Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
    }

    /** Calculates the distance between two points.
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return angle of rotation to make the kart at point1 to face point2 in radians.
     */
    public double calcAngle(double x1, double y1, double x2, double y2)
    {
    	double rotation;
    	if(x1<x2)
    	{
    		rotation = (y1>y2)?
    				Math.atan((x2-x1)/(y1-y2)):(Math.PI-Math.atan((x2-x1)/(y2-y1)));
    	}
    	else
    	{
    		rotation = (y1>y2)?
    				-Math.atan((x1-x2)/(y1-y2)):-(Math.PI-Math.atan((x1-x2)/(y2-y1)));
    	}
    	return rotation;
    }
    
    /** Array with all items.
     * @return An array with all items in the world. 
     */
    public Item[] getItems()
    {
    	return items;
    }
    
    /** Find the path of image of an item. 
     * @param itemId The ID of an item. 
     * @return the address of image of this item. 
     */
    public String findImgPath(int itemId)
    {
    	String imgPath;
    	if(itemId==ItemData.OILCAN)
		{
			imgPath = Game.ASSETS_PATH + "/items/oilcan.png";
		} 
		else if(itemId==ItemData.TOMATO)
		{
			imgPath = Game.ASSETS_PATH + "/items/tomato.png";
		} 
		else if(itemId==ItemData.BOOST)
		{
			imgPath = Game.ASSETS_PATH + "/items/boost.png";
		} 
		else if(itemId==ItemData.OILSLICK)
		{
			imgPath = Game.ASSETS_PATH + "/items/oilslick.png";
		} 
		else
		{
			imgPath = Game.ASSETS_PATH + "/items/tomato-projectile.png";
		}
    	return imgPath;
    }
}
