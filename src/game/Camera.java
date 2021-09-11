/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 */

package game;

// The camera, a rectangle positioned in the world.
public class Camera {
	
    // In pixels
	private final int width;
	private final int height;
    private int left;
    private int top;
    
    // Creates a new Camera centered around the player.
    // width: The width of the camera viewport (pixels).
    // height: The height of the camera viewport (pixels).
    // player: The player, to get the player's location.
    public Camera(int width, int height, Player player) {
    	this.width = width;
    	this.height = height;
        follow(player);
    }
    
    // Move the camera such that the given player is centered.
    public void follow(Player player) {
    	int left = (int) player.getX() - (width / 2);
    	int top = (int) player.getY() - (height / 2);
        moveTo(left, top);
    }

    // Update the camera's x and y coordinates.
    public void moveTo(int left, int top) {
        this.left = left;
        this.top = top;
    }

    // ====================================================================================
    // = Getters & Setters
    // ====================================================================================
    
    // The left x coordinate of the camera (pixels)
    public int getLeft() {
        return left;
    }
    
    // The right x coordinate of the camera (pixels)
    public int getRight() {
        return left + width;
    }
    
    // The top y coordinate of the camera (pixels)
    public int getTop() {
        return top;
    }
    
    // The bottom y coordinate of the camera (pixels)
    public int getBottom() {
        return top + height;
    }
    
    // The width of the camera viewport (pixels)
    public int getWidth() {
    	return width;
    }
    
    // The height of the camera viewport (pixels)
    public int getHeight() {
    	return height;
    }

    

    
}
