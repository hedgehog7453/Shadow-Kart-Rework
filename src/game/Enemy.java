/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.KartData;
import gameData.WayPoints;
import gameData.KartData.Animal;
import helpers.Angle;

// Represents enemy (dog, elephant, octopus)
public abstract class Enemy extends Kart {

	// The index of the enemy's next goal way point
	protected int nextWayPt;

	// Creates a new Enemy.
	public Enemy(Animal type, Angle angle) throws SlickException {
		super(type, angle);
		this.nextWayPt = 0;
	}

	protected double[] getNextWayPt() {
		double wayPtX = WayPoints.getWayPts(nextWayPt)[0];
		double wayPtY = WayPoints.getWayPts(nextWayPt)[1];
		return new double[] {wayPtX, wayPtY};
	}

	// Rotate for a certain angle
	protected void rotate(double rotation) {
		double currAngle = orientation.getRadians();
		if (currAngle * rotation < 0) {
			if (currAngle > 0) {
				int rotateDir = (currAngle-rotation < Math.PI) ? -1 : 1;
				orientation = orientation.add(new Angle(KartData.ROTATE_SPEED * rotateDir));
			} else {
				int rotateDir = (rotation-currAngle < Math.PI) ? 1 : -1;
				orientation = orientation.add(new Angle(KartData.ROTATE_SPEED * rotateDir));
			}
		} else {
			int rotateDir = (currAngle > rotation) ? -1 : 1;
			orientation = orientation.add(new Angle(KartData.ROTATE_SPEED * rotateDir));
		}
	}
}
