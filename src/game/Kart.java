/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** Represents a kart. 
 */
public class Kart extends GameObject
{
	/** Rotation speed, in radians per ms. */
    protected final double ROTATE_SPEED = 0.004;
    /** Acceleration of the kart, in px/ms^2. */
    protected final double ACCELERATION = 0.0005;
    
    /** The distance between karts where collision occurs. */
    protected final int COLLIDE_DISTANCE = 40;
    
    /** Rotate speed of the kart when spinning. */
    protected final double ROTATE_SPEED_SPIN = 0.008;
    
    /** Acceleration while using the boost item, in px/ms^2. */
    protected final double BOOST_ACCELERATION = 0.0008;
    
    /** Angle the kart is currently facing. */
    protected Angle angle;
    /** The kart's current velocity (px/ms). */
    protected double velocity;
    
    /** Number of milliseconds to spin 
     * (kart spins only when it is less than or equals to 700). */
    protected int spinTime;
    /** Number of milliseconds to boost 
     * (kart boosts only when it is less than or equals to 3000). */
    protected int boostTime;
    
    /** Create a new kart. 
     * @param x X coordinate of the kart.
     * @param y Y coordinate of the kart.
     * @param angle Angle the kart is facing. 
     * @param imgPath Address of the image of the kart.
     * @throws SlickException 
     */
    public Kart(double x, double y, Angle angle, String imgPath)
    	    throws SlickException
    {
    	this.img = new Image(imgPath);
    	this.x = x;
    	this.y = y;
    	this.angle = angle;
    	this.velocity = 0;
    	this.spinTime = Item.SPIN_TIME+1;
    }
    
    /** The angle the kart is facing. */
    public Angle getAngle()
    {
        return angle;
    }

    /** Set the time for the kart to spin. 
     * @param spinTime Number of milliseconds to spin the kart. 
     */
    public void setSpinTime(int spinTime)
    {
    	this.spinTime = spinTime;
    }
    
    /** Set the time for the kart to boost. 
     * @param boostTime Number of milliseconds to boost the kart. 
     */
    public void setBoostTime(int boostTime)
    {
    	this.boostTime = boostTime;
    }
    
    /** Draw the kart to the screen at the correct place.
     * @param g The current Graphics context.
     * @param camera The camera to draw relative to.
     */
    public void render(Graphics g, Camera camera)
    {
        // Calculate the kart's on-screen location from the camera
        int screen_x = (int) (x - camera.getLeft());
        int screen_y = (int) (y - camera.getTop());
        img.setRotation((float) angle.getDegrees());
        img.drawCentered(screen_x, screen_y);
    }
    
    /** Count down the number of milliseconds for the kart to spin and 
     * decide if the kart continues spinning in this millisecond. 
     * @param world The world where the kart is in. 
     * @return true if the kart continues spinning. 
     */
    protected boolean spin(World world)
	{
    	if (spinTime==0)
    	{
    		spinTime = Item.SPIN_TIME+1;
    		return false;
    	}
    	if (spinTime<=Item.SPIN_TIME)
    	{
    		spinTime--;
    		return true;
    	}
    	return false;
	}
    
    /** Count down the number of milliseconds for the kart to boost and 
     * decide if the kart continues boosting in this millisecond. 
     * @param world The world where the kart is in. 
     * @return true if the kart continues boosting. 
     */
    protected boolean boost(World world)
	{
    	if (boostTime==0)
    	{
    		boostTime = Item.BOOST_TIME+1;
    		return false;
    	}
    	if (boostTime<=Item.BOOST_TIME)
    	{
    		boostTime--;
    		return true;
    	}
    	return false;
	}
}
