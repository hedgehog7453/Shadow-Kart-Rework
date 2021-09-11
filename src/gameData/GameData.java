package gameData;

public class GameData {
	
	// Paths of assets
    public static final String ASSETS_PATH = "assets";
    public static final String MAP_PATH = ASSETS_PATH + "/map.tmx";
    public static final String PANEL_PATH = ASSETS_PATH + "/panel.png";
    
    // Screen width and height in pixels. */
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 600;

	// The string identifier to use for looking up the map friction
	public static final String MAP_FRICTION_PROPERTY = "friction";
	
	// Y coord of the finishing line
    public static final int GOAL = 1026;
    
    // X, Y coordinates of the final message
    public static final int MESSAGE_X = 360;
    public static final int MESSAGE_Y = 210;
    
    // The "close-enough" distance between kart and waypoint
 	public static final int WAYPT_DISTANCE = 250;
 	
 	// Distance where considering the kart touches the item (in pixels)
    public static final int DISTANCE = 40;

    // Distance to place the item away from the kart (in pixels)
    public static final int PLACE_ITEM_DISTANCE = 41;
    
    
}
