/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.SlickException;

/** Represents enemy.
 */
public class Enemy extends Kart
{
	/** The "close-enough" distance between kart and waypoint. */
	protected final int WAYPT_DISTANCE = 250;
	
	/** The index of the enemy's next goal way point. */
	protected int nextWayPt;
	
	/** All way points */
	protected WayPoints wayPts;
	
	/** Creates a new Enemy.
     * @param x The enemy's initial X location (pixels).
     * @param y The enemy's initial Y location (pixels).
     * @param angle The enemy's initial angle.
     * @param imgPath The address of the image of enemy. 
     * @throws SlickException
     */
	public Enemy(double x, double y, Angle angle, String imgPath)
	throws SlickException
	{
		super(x, y, angle, imgPath);
		this.nextWayPt = 0;
		this.wayPts = new WayPoints();
	}
	
	/** Rotate the enemy to face the target point
	 * @param currAngle The angle the enemy is facing.
	 * @param rotation The degree the enemy needs to rotate.
	 */
	protected void rotate(double currAngle, double rotation)
	{
		int rotate_dir;
		if(currAngle*rotation<0)
		{
			if(currAngle>0) rotate_dir = (currAngle-rotation<Math.PI)?-1:1;
			else rotate_dir = (rotation-currAngle<Math.PI)?1:-1;
		}
		else
		{
			rotate_dir = (currAngle>rotation)?-1:1;
		}
		Angle rotateamount = new Angle(ROTATE_SPEED*rotate_dir);
		angle = angle.add(rotateamount);
	}
}
