/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

import org.newdawn.slick.Image;

public class GameObject {
	/** Image of the object. */
    protected Image img;

    /** X coordinate of the object (pixels). */
    protected double x;
    
    /** Y coordinate of the object (pixels). */
    protected double y;
    
    /** The X coordinate of the object (pixels). */
    public double getX()
    {
        return x;
    }

    /** The Y coordinate of the object (pixels). */
    public double getY()
    {
        return y;
    }
}
