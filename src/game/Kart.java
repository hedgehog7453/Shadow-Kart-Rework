/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameData.ItemData;

// Represents a kart. 
public class Kart extends GameObject {
    
    // Angle the kart is currently facing
    protected Angle angle;
    
    // The kart's current velocity (px/ms)
    protected double velocity;
    
    // Number of milliseconds to spin (kart spins when spinTime <= 700)
    protected int spinTime;
    
    // Number of milliseconds to boost (kart boosts when boostTime <= 3000)
    protected int boostTime;
    
    // Create a new kart. 
    public Kart(double x, double y, Angle angle, String imgPath) throws SlickException {
    	this.img = new Image(imgPath);
    	this.x = x;
    	this.y = y;
    	this.angle = angle;
    	this.velocity = 0;
    	this.spinTime = ItemData.SPIN_TIME+1;
    }
    
    // The angle the kart is facing
    public Angle getAngle() {
        return angle;
    }

    // Set the time for the kart to spin. 
    public void setSpinTime(int spinTime) {
    	this.spinTime = spinTime;
    }
    
    // Set the time for the kart to boost. 
    public void setBoostTime(int boostTime) {
    	this.boostTime = boostTime;
    }
    
    // Draw the kart to the screen at the correct place.
    public void render(Graphics g, Camera camera) {
        // Calculate the kart's on-screen location from the camera
        int screen_x = (int) (x - camera.getLeft());
        int screen_y = (int) (y - camera.getTop());
        img.setRotation((float) angle.getDegrees());
        img.drawCentered(screen_x, screen_y);
    }
    
    // Count down the number of milliseconds for the kart to spin and 
    // decide if the kart continues spinning in this millisecond. 
    protected boolean spin(World world) {
    	if (spinTime == 0) {
    		spinTime = ItemData.SPIN_TIME + 1;
    		return false;
    	}
    	if (spinTime <= ItemData.SPIN_TIME) {
    		spinTime--;
    		return true;
    	}
    	return false;
	}
    
    // Count down the number of milliseconds for the kart to boost and 
    protected boolean boost(World world) {
    	if (boostTime == 0) {
    		boostTime = ItemData.BOOST_TIME + 1;
    		return false;
    	}
    	if (boostTime <= ItemData.BOOST_TIME) {
    		boostTime--;
    		return true;
    	}
    	return false;
	}
}
