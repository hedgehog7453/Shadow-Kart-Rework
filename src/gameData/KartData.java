package gameData;

public class KartData {
	
	// Paths of assets
	public static final String PLAYER_PATH = GameData.ASSETS_PATH + "/karts/donkey.png";
    public static final String ELEP_PATH = GameData.ASSETS_PATH + "/karts/elephant.png";
    public static final String DOG_PATH = GameData.ASSETS_PATH + "/karts/dog.png";
    public static final String OCTO_PATH = GameData.ASSETS_PATH + "/karts/octopus.png";
	
	// Total number of karts in the game
    public static final int NUM_KARTS = 4;
    
    // X and Y coordinate of the start position of karts
    public static final int START_Y = 13086;
    public static final int START_X_ELEPHANT = 1260;
    public static final int START_X_DOG = 1404;
    public static final int START_X_OCTOPUS = 1476;
    public static final int START_X_PLAYER = 1332;
    
    // Acceleration of all kart except for dog, in px/ms^2
    public static final double ACCELERATION = 0.0005;
    // Acceleration when the dog is ahead/behind the player, in px/ms^2
    public static final double DOG_ACC_AHEAD = 0.00045;
    public static final double DOG_ACC_BEHIND = 0.00055;
	
    // The lower/upper bound of distance where the octopus follows player
    public static final int DISTANCE_LOWER = 100;
	public static final int DISTANCE_UPPER = 250;
	
    // Rotation speed when kart is turning direction, in radians per ms
    public static final double ROTATE_SPEED = 0.004;
    
    
    // The distance between when two karts are considered collided
    public static final int COLLIDE_DISTANCE = 40;
    
    
    // Rotation speed when kart is spinning
    public static final double ROTATE_SPEED_SPIN = 0.008;
    // Acceleration when boost item is used, in px/ms^2
    public static final double BOOST_ACCELERATION = 0.0008;
    
    // Number of milliseconds for the kart to spin.
    public static final int SPIN_TIME = 700;
    
    // Number of milliseconds for the kart to boost.
    public static final int BOOST_TIME = 3000;

    public static enum Animal {
		DONKEY,
		ELEPHANT,
		DOG,
		OCTOPUS
	}
	
    public static String getImage(Animal type) {
    	if (type == Animal.DONKEY) {
    		return PLAYER_PATH;
    	} else if (type == Animal.ELEPHANT) {
    		return ELEP_PATH;
    	} else if (type == Animal.DOG) {
    		return DOG_PATH; 
    	} else if (type == Animal.OCTOPUS) {
    		return OCTO_PATH;
    	} else {
    		return "";
    	}
    }
    
    public static int getStartX(Animal type) {
    	if (type == Animal.DONKEY) {
    		return START_X_PLAYER;
    	} else if (type == Animal.ELEPHANT) {
    		return START_X_ELEPHANT;
    	} else if (type == Animal.DOG) {
    		return START_X_DOG; 
    	} else if (type == Animal.OCTOPUS) {
    		return START_X_OCTOPUS;
    	} else {
    		return 0;
    	}
    }
    
    
}
