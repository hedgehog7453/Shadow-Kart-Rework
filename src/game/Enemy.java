/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.KartData;
import gameData.WayPoints;

// Represents enemy.
public class Enemy extends Kart {
	
	// The index of the enemy's next goal way point
	protected int nextWayPt;
	
	// All way points
	protected WayPoints wayPts;
	
	// Creates a new Enemy.
	public Enemy(double x, double y, Angle angle, String imgPath) throws SlickException {
		super(x, y, angle, imgPath);
		this.nextWayPt = 0;
		this.wayPts = new WayPoints();
	}
	
	// Rotate the enemy to face the target point
	protected void rotate(double currAngle, double rotation) {
		int rotate_dir;
		if(currAngle * rotation < 0) {
			if (currAngle > 0) { 
				rotate_dir = (currAngle-rotation < Math.PI) ? -1 : 1;
			} else { 
				rotate_dir = (rotation-currAngle < Math.PI) ? 1 : -1;
			}
		} else {
			rotate_dir = (currAngle > rotation) ? -1 : 1;
		}
		Angle rotateAmount = new Angle(KartData.ROTATE_SPEED * rotate_dir);
		angle = angle.add(rotateAmount);
	}
}
