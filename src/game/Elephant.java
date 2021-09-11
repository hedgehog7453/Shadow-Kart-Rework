/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.GameData;
import gameData.KartData;
import gameData.WayPoints;

// Represents elephant enemy
public class Elephant extends Enemy {
	
    // Create an elephant
	public Elephant(double x, double y, Angle angle, String imgPath) throws SlickException {
		super(x, y, angle, imgPath);
	}
	
	// Update the elephant for a frame.
	public void update(Player player, Dog dog, Octopus octopus, World world) {
		// Find the X and Y coordinates of the next target waypoint.
		double wayPtX = wayPts.getWayPts(nextWayPt)[WayPoints.X_COORD];
		double wayPtY = wayPts.getWayPts(nextWayPt)[WayPoints.Y_COORD];
		
		// Find the rotation needed to make the kart facing next waypoint.
		double rotation = world.calcAngle(x, y, wayPtX, wayPtY);
		
		// Rotate the kart.
		boolean spin = spin(world);
		if (spin) {
			Angle rotateamount = new Angle(KartData.ROTATE_SPEED_SPIN);
			angle = angle.add(rotateamount);
		} else {
			rotate(angle.getRadians(), rotation);
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
        double nextX = this.x + this.angle.getXComponent(amount);
        double nextY = this.y + this.angle.getYComponent(amount);
        if (world.calcDistance(nextX, nextY, player.getX(), player.getY()) < KartData.COLLIDE_DISTANCE ||
        	world.calcDistance(nextX, nextY, dog.getX(), dog.getY()) < KartData.COLLIDE_DISTANCE ||
        	world.calcDistance(nextX, nextY, octopus.getX(), octopus.getY()) < KartData.COLLIDE_DISTANCE ||
        	world.blockingAt((int) nextX, (int) nextY)) {
        	this.velocity = 0;
        } else {
        	this.x = nextX;
        	this.y = nextY;
        }
		
        // If the distance between the kart and the waypoint is less than 250
        // and there exists a next waypoint, add the index of next waypoint by 1.
        double dist = world.calcDistance(x, y, wayPtX, wayPtY);
		if (dist <= GameData.WAYPT_DISTANCE && nextWayPt != WayPoints.NUM_WAYPTS-1) {
			nextWayPt++;
		}
	}
}
