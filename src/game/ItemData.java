/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li <jiayul3>
 */

package game;

/** Information of all items. */
public class ItemData {

	/** The item id of oil can. */
	protected static final int OILCAN = 1;
	/** The item id of tomato. */
	protected static final int TOMATO = 2;
	/** The item id of boost. */
	protected static final int BOOST = 3;
	/** The item id of oil slick. */
	protected static final int OILSLICK = 4;
	/** The item id of tomato projectile. */
	protected static final int TOMATO_PROJ = 5;
	
	/** The index of itemid in the items array. */
	protected static final int ITEM_ID = 0;
	/** The index of x coordinate of the item in the items array. */
	protected static final int ITEM_X = 1;
	/** The index of y coordinate of the item in the items array. */
	protected static final int ITEM_Y = 2;
	
	/** Information of all items. */
	protected static int[][] items = {{OILCAN, 1350, 12438}, 
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
	
	/** The item with index i.
	 * @param i The index. 
	 * @return item with index i.
	 */
	public static int[] getItem(int i)
	{
		return items[i];
	}
}