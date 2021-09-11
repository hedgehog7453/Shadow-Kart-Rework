/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.Image;

public abstract class GameObject {
	
	// Image of the object
    protected Image img;

    // X and Y coordinate of the object in pixels */
    protected double x;
    protected double y;
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
}
