/* SWEN20003 Object Oriented Software Development
 * Shadow Kart
 * Author: Matt Giuca
 */

package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import gameData.GameData;
import gameData.ItemData;

// The status panel.
public class Panel {
	
    // Image for the panel background
    private Image panel;

    // Creates a new Panel
    public Panel() throws SlickException {
        panel = new Image(GameData.PANEL_PATH);
    }

    // Turn a cardinal number into an ordinal.
    public static String ordinal(int ranking) {
        String rank_string = Integer.toString(ranking);
        switch (ranking) {
            case 1:
                rank_string += "st";
                break;
            case 2:
                rank_string += "nd";
                break;
            case 3:
                rank_string += "rd";
                break;
            default:
                rank_string += "th";
        }
        return rank_string;
    }

    // Renders the status panel for the player.
    public void render(Graphics g, int ranking, Item item) {
        // Variables for layout
        int panel_left = GameData.SCREENWIDTH - 86; // Left x coordinate of panel
        int panel_top = GameData.SCREENHEIGHT - 88; // Top y coordinate of panel

        // Panel background image
        panel.draw(panel_left, panel_top);

        // Display the player's ranking
        g.drawString(ordinal(ranking), panel_left + 14, panel_top + 43);

        // Display the player's current item, if any
        if (item != null) {
        	int itemId = item.getItemId();
        	if (itemId != ItemData.TOMATO_PROJ || itemId != ItemData.OILSLICK) {
        		item.drawImage(panel_left + 32, panel_top, null);
        	}
        }
    }
}
