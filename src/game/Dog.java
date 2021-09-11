/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.SlickException;

/** Represents dog. */
public class Dog extends Enemy
{
    /** Acceleration while the dog is ahead of player, in px/ms^2. */
    private static final double ACCELERATION_AHEAD = 0.00045;
    /** Acceleration while the dog is behind of player, in px/ms^2. */
    private static final double ACCELERATION_BACK = 0.00055;
	
    /** Create a dog
     * @param x The dog's initial X location (pixels). 
     * @param y The dog's initial Y location (pixels). 
     * @param angle The dog's initial angle.
     * @param imgPath The address of the image of dog. 
     * @throws SlickException
     */
	public Dog(double x, double y, Angle angle, String imgPath)
	throws SlickException
	{
		super(x, y, angle, imgPath);
	}
	
	/** Update the dog for a frame.
	 * @param player The player.
	 * @param elephant The elephant.
	 * @param octopus The octopus. 
	 * @param world The world the player and enemies are on.
	 */
	public void update(Player player, Elephant elephant, Octopus octopus, World world)
	{
		// Find the X and Y coordinates of the next target waypoint.
		double wayPtX = wayPts.getWayPts(nextWayPt)[WayPoints.X_COORD];
		double wayPtY = wayPts.getWayPts(nextWayPt)[WayPoints.Y_COORD];
				
		// Find the rotation needed to make the kart facing next waypoint.
		double rotation = world.calcAngle(x, y, wayPtX, wayPtY);
		
		// Rotate the kart.
		boolean spin = spin(world);
		if (spin)
		{
			Angle rotateamount = new Angle(ROTATE_SPEED_SPIN);
			angle = angle.add(rotateamount);
		}
		else
			rotate(angle.getRadians(), rotation);
		
        // Determine the friction of the current location
		double friction = world.frictionAt((int) this.x, (int) this.y);
		
		// If the dog is ahead of the player, accelerates at 0.00055 px/ms^2,
		// otherwise 0.00045 px/ms^2.
		double acceleration;
		if(this.y<player.getY()) 
			acceleration = ACCELERATION_AHEAD;
		else 
			acceleration = ACCELERATION_BACK;
		
		// Modify the player's velocity. First, increase due to acceleration.
		this.velocity += acceleration;
		// Then, reduce due to friction (this has the effect of creating a
		// maximum velocity for a given coefficient of friction and
		// acceleration)
		this.velocity *= 1 - friction;

		// Modify the position, based on velocity
		// Calculate the amount to move in each direction
		double amount = this.velocity;
		// Compute the next position, and move to the intended destination
		// if there is no collision and the intended destination is not a blocking tile.
		double nextX = this.x + this.angle.getXComponent(amount);
		double nextY = this.y + this.angle.getYComponent(amount);
		if (world.calcDistance(nextX, nextY, player.getX(), player.getY())<COLLIDE_DISTANCE ||
        		world.calcDistance(nextX, nextY, elephant.getX(), elephant.getY())<COLLIDE_DISTANCE ||
        		world.calcDistance(nextX, nextY, octopus.getX(), octopus.getY())<COLLIDE_DISTANCE ||
        		world.blockingAt((int) nextX, (int) nextY))
        {
        	this.velocity = 0;
        }
        else
        {
        	this.x = nextX;
        	this.y = nextY;
        }
		
		// If the distance between the kart and the waypoint is less than 250
		// and there exists a next waypoint, add the index of next waypoint by 1.
		if(world.calcDistance(x, y, wayPtX, wayPtY)<=WAYPT_DISTANCE && 
				nextWayPt!=WayPoints.NUM_WAYPTS-1)
		{
			nextWayPt++;
		}
	}
}
