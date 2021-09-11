/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.SlickException;

/** The player's kart (Donkey).
 */
public class Player extends Kart
{
	/** The item being held by the player. */
    private Item item;
    
    /** Creates a new Player.
     * @param x The player's initial X location (pixels).
     * @param y The player's initial Y location (pixels).
     * @param angle The player's initial angle.
     * @param imgPath The address of the image of the player. 
     * @throws SlickException
     */
    public Player(double x, double y, Angle angle, String imgPath)
    throws SlickException
    {
        super(x, y, angle, imgPath);
        this.item = null;
        this.boostTime = Item.BOOST_TIME+1;
    }

    /** The item which the player is currently holding. */
    public Item getItem()
    {
    	return item;
    }
    
    /** Set the item which the player is currently holding.
     * @param item The item. 
     */
    public void setItem(Item item)
    {
    	this.item = item;
    }
    
    /** Update the player for a frame.
     * Adjusts the player's angle and velocity based on input, and updates the
     * player's position. Prevents the player from entering a blocking tile.
     * @param rotate_dir The player's direction of rotation
     *      (-1 for anti-clockwise, 1 for clockwise, or 0).
     * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
     * @param world The world the player is on (to get friction and blocking).
     * @param elephant The elephant. 
     * @param dog The dog. 
     * @param octopus The octopus. 
     */
    public void update(double rotate_dir, double move_dir, World world, 
    		Elephant elephant, Dog dog, Octopus octopus)
    {
        // Modify the player's angle
    	boolean spinPlayer = spin(world);
    	Angle rotateamount;
    	if (spinPlayer)
    		rotateamount = new Angle(ROTATE_SPEED_SPIN);
    	else
    		rotateamount = new Angle(ROTATE_SPEED * rotate_dir);
        this.angle = this.angle.add(rotateamount);
        // Determine the friction of the current location
        double friction = world.frictionAt((int) this.x, (int) this.y);
        // Modify the player's velocity. First, increase due to acceleration.
        boolean boostPlayer = boost(world);
        if (boostPlayer)
        	this.velocity += BOOST_ACCELERATION;
        else if (spinPlayer)
        	this.velocity += ACCELERATION;
        else
        	this.velocity += ACCELERATION * move_dir;
        // Then, reduce due to friction (this has the effect of creating a
        // maximum velocity for a given coefficient of friction and
        // acceleration)
        this.velocity *= 1 - friction;

        // Modify the position, based on velocity
        // Calculate the amount to move in each direction
        double amount = this.velocity;
        // Compute the next position, but don't move there yet
        double nextX = this.x + this.angle.getXComponent(amount);
        double nextY = this.y + this.angle.getYComponent(amount);
        // If the intended destination is a blocking tile, do not move there
        // (crash) -- instead, set velocity to 0
        if (world.calcDistance(nextX, nextY, elephant.getX(), elephant.getY())<COLLIDE_DISTANCE ||
    			world.calcDistance(nextX, nextY, dog.getX(), dog.getY())<COLLIDE_DISTANCE ||
    			world.calcDistance(nextX, nextY, octopus.getX(), octopus.getY())<COLLIDE_DISTANCE ||
    			world.blockingAt((int) nextX, (int) nextY))
        {
            this.velocity = 0;
        }
        else
        {
            // Actually move to the intended destination
            this.x = nextX;
            this.y = nextY;
        }
    }
}
