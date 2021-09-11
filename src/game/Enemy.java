/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.KartData;
import gameData.KartData.Animal;
import helpers.Angle;
import gameData.WayPoints;

// Represents enemy (dog, elephant, octopus)
public abstract class Enemy extends Kart {
	
	// All way points
	protected WayPoints wayPts;
	
	// The index of the enemy's next goal way point
	protected int nextWayPt;
	
	// Creates a new Enemy.
	public Enemy(Animal type, Angle angle) throws SlickException {
		super(type, angle);
		this.nextWayPt = 0;
		this.wayPts = new WayPoints();
	}
	
	// Rotate the enemy for a certain angle
	protected void rotate(double rotation) {
		int rotate_dir;
		double currAngle = orientation.getRadians(); 
		if (currAngle * rotation < 0) {
			if (currAngle > 0) { 
				rotate_dir = (currAngle-rotation < Math.PI) ? -1 : 1;
			} else { 
				rotate_dir = (rotation-currAngle < Math.PI) ? 1 : -1;
			}
		} else {
			rotate_dir = (currAngle > rotation) ? -1 : 1;
		}
		Angle rotateAmount = new Angle(KartData.ROTATE_SPEED * rotate_dir);
		orientation = orientation.add(rotateAmount);
	}
	
	protected double[] getNextWayPt() {
		double wayPtX = wayPts.getWayPts(nextWayPt)[WayPoints.X_COORD];
		double wayPtY = wayPts.getWayPts(nextWayPt)[WayPoints.Y_COORD];
		return new double[] {wayPtX, wayPtY};
	}
	
}
