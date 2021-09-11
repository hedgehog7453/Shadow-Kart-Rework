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

// Represents elephant enemy
public class Elephant extends Enemy {
	
    // Create an elephant
	public Elephant() throws SlickException {
		super(Animal.ELEPHANT, Angle.fromDegrees(0));
	}
	
	// Update the elephant for a frame.
	public void update(World world) {
		
		Player player = world.getPlayer();
		Dog dog = world.getDog();
		Octopus octopus = world.getOctopus();
		
		// Find the X and Y coordinates of the next target waypoint.
		double[] nextWp = getNextWayPt();
		
		// Find the rotation needed to make the kart facing next waypoint.
		double rotation = Calculator.calcAngle(x, y, nextWp[0], nextWp[1]);
		
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
        
        // Update the velocity
        this.velocity += KartData.ACCELERATION; // increase due to acceleration
        this.velocity *= 1 - friction; // reduce due to friction

        // Update the position based on velocity
        double amount = this.velocity; // Calculate the amount to move in each direction
        // if there is no collision and the intended destination is not a blocking tile,
        // move to the new position
        // 
        double nextX = this.x + this.orientation.getXComponent(amount);
        double nextY = this.y + this.orientation.getYComponent(amount);
        if (Calculator.calcDistance(nextX, nextY, player.getX(), player.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, dog.getX(), dog.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, octopus.getX(), octopus.getY()) < KartData.COLLIDE_DISTANCE ||
        		world.blockingAt((int) nextX, (int) nextY)) {
        	this.velocity = 0;
        } else {
        	this.x = nextX;
        	this.y = nextY;
        }
		
        // If the distance between the kart and the waypoint is less than 250
        // and there exists a next waypoint, add the index of next waypoint by 1.
        double dist = Calculator.calcDistance(x, y, nextWp[0], nextWp[1]);
		if (dist <= GameData.WAYPT_DISTANCE && nextWayPt != WayPoints.NUM_WAYPTS-1) {
			nextWayPt++;
		}
	}
}
