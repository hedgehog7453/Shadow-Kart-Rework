package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import gameData.GameData;

public class GameMap {
	
	// The world map (two dimensional grid of tiles).
    // The concept of tiles is a private implementation detail to World. All
    // public methods deal with pixels, not tiles.
    private TiledMap map;
    
    public GameMap() throws SlickException {
    	this.map = new TiledMap(GameData.MAP_PATH, GameData.ASSETS_PATH);
    }
    
    // Get the friction coefficient of a map location.
    public double frictionAt(int x, int y) {
        int tile_x = x / map.getTileWidth();
        int tile_y = y / map.getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String friction = map.getTileProperty(tileid, GameData.MAP_FRICTION_PROPERTY, null);
        return Double.parseDouble(friction);
    }
    
    // Determines whether a particular map location blocks movement.
    public boolean blockingAt(int x, int y) {
        return frictionAt(x, y) >= 1;
    }
    
    public int getTileWidth() {
    	return map.getTileWidth();
    }
    
    public int getTileHeight() {
    	return map.getTileHeight();
    }
    
    // Render the map according to camera borders
    public void renderMap(int left, int top, int width, int height) {
    	// Calculate the camera location (in tiles) and offset (in pixels).
        // Render 24x18 tiles of the map to the screen, starting from the
        // camera location in tiles (rounded down). Begin drawing at a
        // negative offset relative to the screen, to ensure smooth scrolling.
        int cam_tile_x = left / map.getTileWidth();
        int cam_tile_y = top / map.getTileHeight();
        int cam_offset_x = left % map.getTileWidth();
        int cam_offset_y = top % map.getTileHeight();        
        int screen_tilew = width / map.getTileWidth() + 2;
        int screen_tileh = height / map.getTileHeight() + 2;
        map.render(-cam_offset_x, -cam_offset_y, cam_tile_x, cam_tile_y, 
        		screen_tilew, screen_tileh);
    }
    
}
