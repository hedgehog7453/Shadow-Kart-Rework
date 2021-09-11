/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.SlickException;

/** Represents elephant. */
public class Elephant extends Enemy
{
    /** Create an elephant
     * @param x The elephant's initial X location (pixels). 
     * @param y The elephant's initial Y location (pixels). 
     * @param angle The elephant's initial angle.
     * @param imgPath The address of the image of elephant. 
     * @throws SlickException
     */
	public Elephant(double x, double y, Angle angle, String imgPath)
	throws SlickException
	{
		super(x, y, angle, imgPath);
	}
	
	/** Update the elephant for a frame.
	 * @param player The player.
	 * @param dog The dog.
	 * @param octopus The octopus. 
	 * @param world The world the player and enemies are on.
	 */
	public void update(Player player, Dog dog, Octopus octopus, World world)
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
        // Modify the player's velocity. First, increase due to acceleration.
        this.velocity += ACCELERATION;
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
        		world.calcDistance(nextX, nextY, dog.getX(), dog.getY())<COLLIDE_DISTANCE ||
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
