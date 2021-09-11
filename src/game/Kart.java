/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import gameData.KartData;
import gameData.KartData.Animal;
import helpers.Angle;

// Represents a kart. 
public abstract class Kart extends GameObject {

	// Type of animal
	protected Animal type;
	
    // Angle the kart is currently facing
    protected Angle orientation;
    
    // The kart's current velocity (px/ms)
    protected double velocity;
    
    // Number of milliseconds to spin.
    // kart spins and spinTime counts down when 0 < spinTime <= 700
    protected int spinTime;
    
    // Number of milliseconds to boost.
    // kart boosts and boostTime counts down when 0 < boostTime <= 3000
    protected int boostTime;
    
    // Create a new kart
    public Kart(Animal type, Angle orientation) throws SlickException {
    	super(KartData.getStartX(type), KartData.START_Y, KartData.getImage(type));
    	this.orientation = orientation;
    	this.velocity = 0;
    	this.spinTime = KartData.SPIN_TIME + 1;
    	this.boostTime = KartData.BOOST_TIME + 1;
    }
    
    // Count down the number of milliseconds for the kart to spin and 
    // decide if the kart continues spinning in this millisecond. 
    protected boolean spin() {
    	if (spinTime == 0) {
    		spinTime = KartData.SPIN_TIME + 1;
    		return false;
    	}
    	if (spinTime <= KartData.SPIN_TIME) {
    		spinTime--;
    		return true;
    	}
    	return false;
	}
    
    // Count down the number of milliseconds for the kart to boost and 
    protected boolean boost() {
    	if (boostTime == 0) {
    		boostTime = KartData.BOOST_TIME + 1;
    		return false;
    	}
    	if (boostTime <= KartData.BOOST_TIME) {
    		boostTime--;
    		return true;
    	}
    	return false;
	}
    
    // ====================================================================================
    // = Rendering
    // ====================================================================================
    
    // Draw the kart to the screen at the correct place.
    public void render(Graphics g, Camera camera) {
        // Calculate the kart's on-screen location from the camera
        int screen_x = (int) (x - camera.getLeft());
        int screen_y = (int) (y - camera.getTop());
        img.setRotation((float) orientation.getDegrees());
        img.drawCentered(screen_x, screen_y);
    }
    
    // ====================================================================================
    // = Getters & Setters
    // ====================================================================================
    
    // The angle the kart is facing
    public Angle getAngle() {
        return orientation;
    }

    // Set the time for the kart to spin. 
    public void setSpinTime(int spinTime) {
    	this.spinTime = spinTime;
    }
    
    // Set the time for the kart to boost. 
    public void setBoostTime(int boostTime) {
    	this.boostTime = boostTime;
    }
    
}
