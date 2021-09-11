/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.GameData;
import gameData.KartData;
import gameData.KartData.Animal;
import gameData.WayPoints;
import helpers.Angle;
import helpers.Calculator;

// Represents octopus enemy
public class Octopus extends Enemy {
	
	// Create an octopus
	public Octopus() throws SlickException {
		super(Animal.OCTOPUS, Angle.fromDegrees(0));
	}
	
	// Update the octopus for a frame.
	public void update(World world) {
		
		Player player = world.getPlayer();
		Elephant elephant = world.getElephant();
		Dog dog = world.getDog(); 
		
		// Find the distance between the player and octopus.
		double distance = Calculator.calcDistance(x, y, player.getX(), player.getY());
		
		// If the octopus is between 100 and 250 pixels away from the player, 
		// rotation = the radian the octopus needs to rotate to face the player,
		// otherwise to face the next waypoint.
		double rotation;
		if (distance > KartData.DISTANCE_LOWER 
				&& distance < KartData.DISTANCE_UPPER) {
			rotation = Calculator.calcAngle(x, y, player.getX(), player.getY());
			nextWayPt = nearestWayPt(world);
		} else {
			// Find the X and Y coordinates of the next target waypoint.
			double[] nextWp = getNextWayPt();
			
			// Find the rotation needed to make the kart facing next waypoint.
			rotation = Calculator.calcAngle(x, y, nextWp[0], nextWp[1]);
			
			// If the distance between the kart and the waypoint is less than 250
			// and there exists a next waypoint, add the index of next waypoint by 1.
			double dist = Calculator.calcDistance(x, y, nextWp[0], nextWp[1]);
			if (dist <= GameData.WAYPT_DISTANCE && nextWayPt != WayPoints.NUM_WAYPTS-1) {
				nextWayPt++;
			}
		}
		
		// Rotate the kart.
		boolean spin = spin();
		if (spin) {
			Angle rotateamount = new Angle(KartData.ROTATE_SPEED_SPIN);
			orientation = orientation.add(rotateamount);
		} else {
			rotate(rotation);
		}
		
		// Determine the friction of the current location
		double friction = world.frictionAt((int) this.x, (int) this.y);
		
		// Update the player's velocity
		this.velocity += KartData.ACCELERATION; // increase due to acceleration
		this.velocity *= 1 - friction; // reduce due to friction

		// Update the position based on velocity
        double amount = this.velocity; // Calculate the amount to move in each direction
        
     // if there is no collision and the intended destination is not a blocking tile, 
     		// move to the new position
        double nextX = this.x + this.orientation.getXComponent(amount);
        double nextY = this.y + this.orientation.getYComponent(amount);
        if (Calculator.calcDistance(nextX, nextY, player.getX(), player.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, elephant.getX(), elephant.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, dog.getX(), dog.getY()) < KartData.COLLIDE_DISTANCE ||
        		world.blockingAt((int) nextX, (int) nextY)) {
            this.velocity = 0;
        } else {
            this.x = nextX;
            this.y = nextY;
        }
	}
	
	/** Find the waypoint nearest to the octopus. 
	 * @param world The world there the octopus is in. 
	 * @return the index of the nearest waypoint. 
	 */
	private int nearestWayPt(World world) {
		double wpX = WayPoints.getWayPts(0)[0];
		double wpY = WayPoints.getWayPts(0)[1];
		double min = Calculator.calcDistance(x, y, wpX, wpY);
		int nearestWayPt = 0;
		for (int i = 0; i < WayPoints.NUM_WAYPTS; i++) {
			wpX = WayPoints.getWayPts(i)[0];
			wpY = WayPoints.getWayPts(i)[1];
			if (Calculator.calcDistance(x, y, wpX, wpY) < min) {
				nearestWayPt=i;
			}
		}
		return nearestWayPt;
	}
}
