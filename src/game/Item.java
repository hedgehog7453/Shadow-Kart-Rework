/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Jiayu Li
 */

package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import gameData.GameData;
import gameData.ItemData;
import gameData.KartData;
import helpers.Angle;
import helpers.Calculator;

// Represent an item
public class Item extends GameObject {
	
	// Item Id of the item
	private int itemId;
	
	// Existence of the item
	private boolean exist;
	// Index of the item in the item array in World
	private int itemIndex;
	
	// Create a new item. 
	public Item(int itemIndex, int itemX, int itemY, int itemId, String imgPath) throws SlickException {
		super(itemX, itemY, imgPath);
		this.itemIndex = itemIndex;
		this.itemId = itemId;
		this.exist = true;
	}
	
	// Index of the item in the array in World
	public int getItemIndex() {
		return itemIndex;
	}
	
	// Item Id of the item
	public int getItemId() {
		return itemId;
	}
	
	// Existence of the item
	public boolean getExist() {
		return exist;
	}
	
	// Use the item. 
	public void useItem(World world, Player player) throws SlickException {
		if (itemId == ItemData.BOOST) {
			player.setBoostTime(KartData.BOOST_TIME);
			player.setItem(null);
		}
		else if (itemId == ItemData.TOMATO) {
			changeItem(player, world, ItemData.TOMATO);
			player.setItem(null);
		}
		else if (itemId == ItemData.OILCAN) {
			changeItem(player, world, ItemData.OILCAN);
			player.setItem(null);
		}
	}
	
	// Check if the distance between item and a particular point is less than 40.
	private boolean checkDistance(World world, double pointX, double pointY) {
		double distance = Calculator.calcDistance(pointX, pointY, x, y);
		if (distance < GameData.DISTANCE) {
			return true;
		} else {
			return false;
		}
	}
	
	// Update the item except for tomato projectile. 
	public void update(World world, Player player, Elephant elephant, Dog dog, Octopus octopus) {
		// If the distance between the player and item is less than 40 pixels, 
		// and the item is not a hazard, the player can pick up the item.
		if (checkDistance(world, player.getX(), player.getY())) {
			if (itemId == ItemData.OILSLICK) {
				player.setSpinTime(KartData.SPIN_TIME);
			} else {
				player.setItem(this);
			}
			exist = false;
		}
		
		// If the item is oil slick, set the kart
		// (which is less than 40 pixels away from the oil slick) to spin. 
		if (itemId == ItemData.OILSLICK) {
			if (checkDistance(world, player.getX(), player.getY())) {
				player.setSpinTime(KartData.SPIN_TIME);
				exist = false;
			} else if (checkDistance(world, elephant.getX(), elephant.getY())) {
				elephant.setSpinTime(KartData.SPIN_TIME);
				exist = false;
			} else if (checkDistance(world, dog.getX(), dog.getY())) {
				dog.setSpinTime(KartData.SPIN_TIME);
				exist = false;
			} else if (checkDistance(world, octopus.getX(), octopus.getY())) {
				octopus.setSpinTime(KartData.SPIN_TIME);
				exist = false;
			}
		}
	}
	
	// Update projectile tomato.
	public void updateTomato(World world, Player player, Elephant elephant, Dog dog, Octopus octopus) {
		Angle direction = player.getAngle();
		// Compute the next position. 
		double nextX = this.x + direction.getXComponent(ItemData.TOMATO_SPEED);
		double nextY = this.y + direction.getYComponent(ItemData.TOMATO_SPEED);
		img.setRotation((float)direction.getDegrees());
		
		// If the tomato hits a kart, set the kart to spin and remove the projectile tomato. 
		if (checkDistance(world, player.getX(), player.getY())) {
			player.setSpinTime(KartData.SPIN_TIME);
			exist = false;
		} else if (checkDistance(world, elephant.getX(), elephant.getY())) {
			elephant.setSpinTime(KartData.SPIN_TIME);
			exist = false;
		} else if (checkDistance(world, dog.getX(), dog.getY())) {
			dog.setSpinTime(KartData.SPIN_TIME);
			exist = false;
		} else if (checkDistance(world, octopus.getX(), octopus.getY())) {
			octopus.setSpinTime(KartData.SPIN_TIME);
			exist = false;
		} else if (world.blockingAt((int) nextX, (int) nextY)) {
			// If the tomato hits a blocking tile, set the existence of the tomato to false.
			exist = false;
		} else {
			// If nothing is hit, move the tomato to the next position.
			this.x = nextX;
			this.y = nextY;
		}
	}
	
	// Render the item if it is in the screen. 
	public void render(Graphics g, Camera camera) {
		if (this.x > camera.getLeft() && this.x < camera.getRight() 
				&& this.y > camera.getTop() && this.y < camera.getBottom() && exist) {
			int screen_x = (int) (x - camera.getLeft());
			int screen_y = (int) (y - camera.getTop());
			img.drawCentered(screen_x, screen_y);
		}
	}
	
	// Draw an item 
	public void drawImage(int x, int y, Angle rotation) {
		if (rotation != null)
			img.setRotation((float)rotation.getDegrees());
		img.draw(x, y);
	}
	
	// Change an item to hazard. (oil can and tomato)
	private void changeItem(Player player, World world, int itemId) throws SlickException {
		Angle rotation = player.getAngle();
		
		if (itemId == ItemData.TOMATO) {
			x = player.getX() + rotation.getXComponent(GameData.PLACE_ITEM_DISTANCE);
			y = player.getY() + rotation.getYComponent(GameData.PLACE_ITEM_DISTANCE);
			img = new Image(ItemData.getItemImgPath(ItemData.TOMATO_PROJ));
			this.itemId = ItemData.TOMATO_PROJ;
		} else if (itemId==ItemData.OILCAN) {
			double radians = rotation.getRadians();
			double reversedRadians = (radians>0)?(radians-Math.PI):(radians+Math.PI);
			Angle newAngle = new Angle(reversedRadians);
			x = player.getX() + newAngle.getXComponent(GameData.PLACE_ITEM_DISTANCE);
			y = player.getY() + newAngle.getYComponent(GameData.PLACE_ITEM_DISTANCE);
			img = new Image(ItemData.getItemImgPath(ItemData.OILSLICK));
			this.itemId = ItemData.OILSLICK;
		}
		exist = true;
	}
}