/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package gameData;

public class ItemData {
	
	// Asset paths
	public static final String OILCAN_PATH = GameData.ASSETS_PATH + "/items/oilcan.png";
	public static final String TOMATO_PATH = GameData.ASSETS_PATH + "/items/tomato.png";;
	public static final String BOOST_PATH = GameData.ASSETS_PATH + "/items/boost.png";
	public static final String OILSLICK_PATH = GameData.ASSETS_PATH + "/items/oilslick.png";
	public static final String TOMATO_PROJ_PATH = GameData.ASSETS_PATH + "/items/tomato-projectile.png";

	// Number of items in this world
    public static final int ITEMS = 12;
    
	// item ids
	public static final int OILCAN = 1;
	public static final int TOMATO = 2;
	public static final int BOOST = 3;
	public static final int OILSLICK = 4;
	public static final int TOMATO_PROJ = 5;
	
	// The index of itemid in the items array
	public static final int ITEM_ID = 0;
	// The index of x,y coordinate of the item in the items array
	public static final int ITEM_X = 1;
	public static final int ITEM_Y = 2;
	
	// items and their coordinates
	private static int[][] items = {
			{OILCAN, 1350, 12438}, 
			{TOMATO, 990, 11610}, 
			{BOOST, 990, 10242}, 
			{OILCAN, 864, 7614}, 
			{OILCAN, 1962, 6498}, 
			{BOOST, 1818, 6534}, 
			{TOMATO, 1206, 5130}, 
			{BOOST, 990, 4302}, 
			{TOMATO, 1206, 3690}, 
			{OILCAN, 1314, 3690}, 
			{BOOST, 1926, 3510}, 
			{TOMATO, 1422, 2322}};
	
	// Number of milliseconds for the kart to spin.
    public static final int SPIN_TIME = 700;
    
    // Number of milliseconds for the kart to boost.
    public static final int BOOST_TIME = 3000;
    
    // Speed of tomato projectile (in px/ms).
    public static final double TOMATO_SPEED = 1.7;
    
	// Get the item with index i
	public static int[] getItem(int i) {
		return items[i];
	}
}