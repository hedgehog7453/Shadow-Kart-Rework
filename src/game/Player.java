/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.SlickException;

import gameData.KartData;
import gameData.KartData.Animal;
import helpers.Angle;
import helpers.Calculator;

// The player's kart (Donkey).
public class Player extends Kart {

	// The item being held by the player
    private Item item;
    
    // Creates a new Player.
    public Player() throws SlickException {
        super(Animal.DONKEY, Angle.fromDegrees(0));
        this.item = null;
        this.boostTime = KartData.BOOST_TIME + 1;
    }

    // The item which the player is currently holding
    public Item getItem() {
    	return item;
    }
    
    // Set the item which the player is currently holding
    public void setItem(Item item) {
    	this.item = item;
    }
    
    // Update the player for a frame.
    public void update(double rotate_dir, double move_dir, World world) {
    	
    	Elephant elephant = world.getElephant();
    	Dog dog = world.getDog();
    	Octopus octopus = world.getOctopus();
    	
        // Modify the player's angle
    	boolean spinPlayer = spin(world);
    	double rotateSpeed = spinPlayer ? KartData.ROTATE_SPEED_SPIN : KartData.ROTATE_SPEED * rotate_dir;
    	Angle rotateamount = new Angle(rotateSpeed);
        this.orientation = this.orientation.add(rotateamount);
        // Determine the friction of the current location
        double friction = world.frictionAt((int) this.x, (int) this.y);
        // Update the player's velocity
        // increase due to acceleration
        boolean boostPlayer = boost(world);
        if (boostPlayer) {
        	this.velocity += KartData.BOOST_ACCELERATION;
        } else if (spinPlayer) {
        	this.velocity += KartData.ACCELERATION;
        } else {
        	this.velocity += KartData.ACCELERATION * move_dir;
        }
        
        this.velocity *= 1 - friction; // reduce due to friction

        // Update the position based on velocity
        double amount = this.velocity; // calculate the amount to move in each direction
        // Compute the next position, but don't move there yet
        double nextX = this.x + this.orientation.getXComponent(amount);
        double nextY = this.y + this.orientation.getYComponent(amount);
        // If the intended destination is a blocking tile, do not move there.
        // instead, set velocity to 0
        if (Calculator.calcDistance(nextX, nextY, elephant.getX(), elephant.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, dog.getX(), dog.getY()) < KartData.COLLIDE_DISTANCE ||
        		Calculator.calcDistance(nextX, nextY, octopus.getX(), octopus.getY()) < KartData.COLLIDE_DISTANCE ||
        		world.blockingAt((int) nextX, (int) nextY)) {
            this.velocity = 0;
        } else {
            // move to the intended destination
            this.x = nextX;
            this.y = nextY;
        }
    }
}
