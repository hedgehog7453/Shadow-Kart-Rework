package gameData;

public class KartData {
	
	// Paths of assets
	public static final String PLAYER_PATH = GameData.ASSETS_PATH + "/karts/donkey.png";
    public static final String ELEP_PATH = GameData.ASSETS_PATH + "/karts/elephant.png";
    public static final String DOG_PATH = GameData.ASSETS_PATH + "/karts/dog.png";
    public static final String OCTO_PATH = GameData.ASSETS_PATH + "/karts/octopus.png";
	
	// Total number of karts in the game
    public static final int NUM_KARTS = 4;
    
    // Y coordinate of the start position of all karts
    public static final int START_Y = 13086;
    
    // X coordinate of the start position of elephant
    public static final int START_X_ELEPHANT = 1260;
    
    // X coordinate of the start position of dog
    public static final int START_X_DOG = 1404;
    // Acceleration when the dog is ahead of player, in px/ms^2
    public static final double DOG_ACC_AHEAD = 0.00045;
    // Acceleration when the dog is behind of player, in px/ms^2
    public static final double DOG_ACC_BEHIND = 0.00055;
	
    // The upper bound of distance where the octopus follows player
	public static final int DISTANCE_UPPER = 250;
	// The lower bound of distance where the octopus follows player
	public static final int DISTANCE_LOWER = 100;
	
    // X coordinate of the start position of octopus
    public static final int START_X_OCTOPUS = 1476;
    
    // X coordinate of the start position of the player
    public static final int START_X_PLAYER = 1332;
    
    // Rotation speed, in radians per ms
    public static final double ROTATE_SPEED = 0.004;
    
    // Acceleration of the kart in px/ms^2
    public static final double ACCELERATION = 0.0005;
    
    // The distance between karts where collision occurs
    public static final int COLLIDE_DISTANCE = 40;
    
    // Rotate speed of the kart when spinning
    public static final double ROTATE_SPEED_SPIN = 0.008;
    
    // Acceleration while using the boost item, in px/ms^2
    public static final double BOOST_ACCELERATION = 0.0008;

}
