/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.SlickException;

/** Represents octopus. */
public class Octopus extends Enemy
{
	/** The upper bound of distance where the octopus follows player. */
	private final int DISTANCE_UPPER = 250;
	/** The lower bound of distance where the octopus follows player. */
	private final int DISTANCE_LOWER = 100;
	
	/** Create an octopus
     * @param x The octopus's initial X location (pixels). 
     * @param y The octopus's initial Y location (pixels). 
     * @param angle The octopus's initial angle.
     * @param imgPath The address of the image of octopus. 
     * @throws SlickException
     */
	public Octopus(double x, double y, Angle angle, String imgPath)
	throws SlickException
	{
		super(x, y, angle, imgPath);
	}
	
	/** Update the octopus for a frame.
	 * @param player The player.
	 * @param elephant The elephant.
	 * @param dog The dog.
	 * @param world The world the player and enemies are on.
	 */
	public void update(Player player, Elephant elephant, Dog dog, World world)
	{
		// Find the distance between the player and octopus.
		double distance;
		distance = world.calcDistance(x, y, player.getX(), player.getY());
		
		// If the octopus is between 100 and 250 pixels away from the player, 
		// rotation is the radian the octopus needs to rotate to face the player,
		// otherwise to face the next waypoint.
		double rotation;
		if(distance>DISTANCE_LOWER && distance<DISTANCE_UPPER)
		{
			rotation = world.calcAngle(x, y, player.getX(), player.getY());
			nextWayPt = nearestWayPt(world);
		}
		else
		{
			// Find the X and Y coordinates of the next target waypoint.
			double wayPtX = wayPts.getWayPts(nextWayPt)[WayPoints.X_COORD];
			double wayPtY = wayPts.getWayPts(nextWayPt)[WayPoints.Y_COORD];
			
			// Find the rotation needed to make the kart facing next waypoint.
			rotation = world.calcAngle(x, y, wayPtX, wayPtY);
			
			// If the distance between the kart and the waypoint is less than 250
			// and there exists a next waypoint, add the index of next waypoint by 1.
			if(world.calcDistance(x, y, wayPtX, wayPtY)<=WAYPT_DISTANCE && 
					nextWayPt!=WayPoints.NUM_WAYPTS-1)
			{
				nextWayPt++;
			}
		}
		
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
        		world.calcDistance(nextX, nextY, elephant.getX(), elephant.getY())<COLLIDE_DISTANCE ||
        		world.calcDistance(nextX, nextY, dog.getX(), dog.getY())<COLLIDE_DISTANCE ||
        		world.blockingAt((int) nextX, (int) nextY))
        {
            this.velocity = 0;
        }
        else
        {
            this.x = nextX;
            this.y = nextY;
        }
	}
	
	/** Find the waypoint nearest to the octopus. 
	 * @param world The world there the octopus is in. 
	 * @return the index of the nearest waypoint. 
	 */
	private int nearestWayPt(World world)
	{
		double min=world.calcDistance(x, y, 
				wayPts.getWayPts(0)[WayPoints.X_COORD], 
				wayPts.getWayPts(0)[WayPoints.Y_COORD]);
		int nearestWayPt=0;
		for(int i=0;i<WayPoints.NUM_WAYPTS;i++)
		{
			if(world.calcDistance(x, y, 
					wayPts.getWayPts(i)[WayPoints.X_COORD], 
					wayPts.getWayPts(i)[WayPoints.Y_COORD])<min)
			{
				nearestWayPt=i;
			}
		}
		return nearestWayPt;
	}
}
