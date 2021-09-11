/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

/** Represent an item. */
public class Item extends GameObject
{
	/** Number of milliseconds for the kart to spin. */
    protected static final int SPIN_TIME = 700;
    /** Number of milliseconds for the kart to boost. */
    protected static final int BOOST_TIME = 3000;
    
    /** Distance where considering the kart touches the item (in pixels). */
    private final int DISTANCE = 40;
    
    /** Distance to place the item away from the kart (in pixels). */
    private final int PLACE_ITEM_DISTANCE = 41;
    
    /** Speed of tomato projectile (in px/ms). */
    private final double TOMATO_SPEED = 1.7;
	
	/** Item Id of the item. */
	private int itemId;
	
	/** Existence of the item. */
	private boolean exist;
	/** Index of the item in the item array in World. */
	private int itemIndex;
	
	
	/** Create a new item. 
	 * @param itemIndex The index of the item in the array in World. 
     * @param itemX X coordinate of the item.
     * @param itemY Y coordinate of the item.
     * @param itemId ID of the item. 
     * @param imgPath Address of the image of the item.
     * @throws SlickException 
     */
	public Item(int itemIndex, int itemX, int itemY, int itemId, String imgPath) 
			throws SlickException
	{
		this.itemIndex = itemIndex;
		this.x = itemX;
		this.y = itemY;
		this.itemId = itemId;
		this.img = new Image(imgPath);
		this.exist = true;
	}
	
	/** Index of the item in the array in World. */
	public int getItemIndex()
	{
		return itemIndex;
	}
	
	/** Item Id of the item. */
	public int getItemId()
	{
		return itemId;
	}
	
	/** Existence of the item. */
	public boolean getExist()
	{
		return exist;
	}
	
	/** Use the item. 
	 * @param world The world where the item is in.
	 * @param player The player who uses the item.
	 * @throws SlickException
	 */
	public void useItem(World world, Player player) throws SlickException
	{
		if (itemId==ItemData.BOOST)
		{
			player.setBoostTime(BOOST_TIME);
			player.setItem(null);
		}
		else if (itemId==ItemData.TOMATO)
		{
			changeItem(player, world, ItemData.TOMATO);
			player.setItem(null);
		}
		else if (itemId==ItemData.OILCAN)
		{
			changeItem(player, world, ItemData.OILCAN);
			player.setItem(null);
		}
	}
	
	/** Check if the distance between item and a particular point is less than 40.
	 * @param world The world where item is in. 
	 * @param x The x coordinate of the point. 
	 * @param y The y coordinate of the point. 
	 * @return true if the distance is less than 40.
	 */
	private boolean checkDistance(World world, double pointX, double pointY)
	{
		double distance = world.calcDistance(pointX, pointY, x, y);
		if (distance<DISTANCE)
			return true;
		else
			return false;
	}
	
	/** Update the item except for tomato projectile. 
	 * @param world The world where item is in. 
	 * @param player The player.
	 * @param elephant The elephant. 
	 * @param dog The dog. 
	 * @param octopus The octopus. 
	 */
	public void update(World world, Player player, Elephant elephant, Dog dog, Octopus octopus)
	{
		// If the distance between the player and item is less than 40 pixels, 
		// and the item is not a hazard, the player can pick up the item.
		if (checkDistance(world, player.getX(), player.getY())) {
			if (itemId==ItemData.OILSLICK)
				player.setSpinTime(SPIN_TIME);
			else
				player.setItem(this);
			exist = false;
		}
		
		// If the item is oil slick, set the kart
		// (which is less than 40 pixels away from the oil slick) to spin. 
		if (itemId==ItemData.OILSLICK)
		{
			if (checkDistance(world, player.getX(), player.getY()))
			{
				player.setSpinTime(SPIN_TIME);
				exist = false;
			}
			else if (checkDistance(world, elephant.getX(), elephant.getY()))
			{
				elephant.setSpinTime(SPIN_TIME);
				exist = false;
			}
			else if (checkDistance(world, dog.getX(), dog.getY()))
			{
				dog.setSpinTime(SPIN_TIME);
				exist = false;
			}
			else if (checkDistance(world, octopus.getX(), octopus.getY()))
			{
				octopus.setSpinTime(SPIN_TIME);
				exist = false;
			}
		}
	}
	
	/** Update projectile tomato.
	 * @param world The world where the projectile tomato is in. 
	 * @param player The player. 
	 * @param elephant The elephant. 
	 * @param dog The dog. 
	 * @param octopus The octopus. 
	 */
	public void updateTomato(World world, Player player, Elephant elephant, Dog dog, Octopus octopus)
	{
		Angle direction = player.getAngle();
		// Compute the next position. 
		double nextX = this.x + direction.getXComponent(TOMATO_SPEED);
		double nextY = this.y + direction.getYComponent(TOMATO_SPEED);
		img.setRotation((float)direction.getDegrees());
		
		// If the tomato hits a kart, set the kart to spin and remove the projectile tomato. 
		if (checkDistance(world, player.getX(), player.getY()))
		{
			player.setSpinTime(SPIN_TIME);
			exist = false;
		}
		else if (checkDistance(world, elephant.getX(), elephant.getY()))
		{
			elephant.setSpinTime(SPIN_TIME);
			exist = false;
		}
		else if (checkDistance(world, dog.getX(), dog.getY()))
		{
			dog.setSpinTime(SPIN_TIME);
			exist = false;
		}
		else if (checkDistance(world, octopus.getX(), octopus.getY()))
		{
			octopus.setSpinTime(SPIN_TIME);
			exist = false;
		}
		// If the tomato hits a blocking tile, set the existence of the tomato to false. 
		else if (world.blockingAt((int) nextX, (int) nextY))
		{
			exist = false;
		}
		// If nothing is hit, move the tomato to the next position. 
		else 
		{
			this.x = nextX;
			this.y = nextY;
		}
	}
	
	/** Render the item if it is in the screen. 
	 * @param g The current Graphics context.
     * @param camera The camera to draw relative to.
	 */
	public void render(Graphics g, Camera camera)
	{
		if(this.x > camera.getLeft() && 
				this.x < camera.getRight() && 
				this.y > camera.getTop() && 
				this.y < camera.getBottom() && exist)
		{
			int screen_x = (int) (x - camera.getLeft());
			int screen_y = (int) (y - camera.getTop());
			img.drawCentered(screen_x, screen_y);
		}
	}
	
	/** Draw an item. 
	 * @param x The X coordinate of the item with respect to screen. 
	 * @param y The Y coordinate of the item with respect to screen. 
	 * @param rotation The angle of rotation of item.
	 */
	public void drawImage(int x, int y, Angle rotation)
	{
		if (rotation!=null)
			img.setRotation((float)rotation.getDegrees());
		img.draw(x, y);
	}
	
	/** Change an item to hazard. (oil can and tomato)
	 * @param player The player who use the item.
	 * @param world The world where the player and item are in. 
	 * @param itemId The item ID of the item. 
	 * @throws SlickException
	 */
	private void changeItem(Player player, World world, int itemId) throws SlickException
	{
		Angle rotation = player.getAngle();
		
		if (itemId==ItemData.TOMATO)
		{
			x = player.getX() + rotation.getXComponent(PLACE_ITEM_DISTANCE);
			y = player.getY() + rotation.getYComponent(PLACE_ITEM_DISTANCE);
			img = new Image(world.findImgPath(ItemData.TOMATO_PROJ));
			this.itemId = ItemData.TOMATO_PROJ;
		}
		else if (itemId==ItemData.OILCAN)
		{
			double radians = rotation.getRadians();
			double reversedRadians = (radians>0)?(radians-Math.PI):(radians+Math.PI);
			Angle newAngle = new Angle(reversedRadians);
			
			x = player.getX() + newAngle.getXComponent(PLACE_ITEM_DISTANCE);
			y = player.getY() + newAngle.getYComponent(PLACE_ITEM_DISTANCE);
			img = new Image(world.findImgPath(ItemData.OILSLICK));
			this.itemId = ItemData.OILSLICK;
		}
		exist = true;
	}
}