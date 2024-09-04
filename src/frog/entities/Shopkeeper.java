package frog.entities;

import java.util.Map;

import frog.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a shopkeeper which you can interact with to bring up a shop GUI.
 * @author Jeremy Mills
 *
 */
public class Shopkeeper extends Entity{
	
	private PImage shopkeeper;


	public Shopkeeper(double x, double y, double width, double height, double health, PApplet marker) {
		super(x, y, width, height, health);
		shopkeeper = marker.loadImage("resources/shopkeeper.png");
	}
	
	public Shopkeeper(Map<String, Object> map, PApplet marker) {
		super(map);
		shopkeeper = marker.loadImage("resources/shopkeeper.png");
	}
	
	/**
	 * Draws the Shopkeeper in the Grid
	 */
	public void draw(PApplet marker) {
		marker.fill(255);
		//marker.image(shopkeeper, (float)(x-width/2), (float)(y-height/2), (float)width, (float)height);
		marker.image(shopkeeper, (float)x, (float)y, (float)width, (float)height);
		//marker.ellipse((float)x, (float)y, (float)width, (float)height);
		
	}

	/**
	 * Changes the screen to the Shopkeeper GUI
	 * @param surface, the drawingsurface to switch screens.
	 */
	public void changeScreen(DrawingSurface surface) {
		//System.out.println("yaya");
		surface.switchScreen(surface.SHOPKEEPER_GUI);
	}
	
	/**
	 * Returns the shopkeeper image
	 */
	public PImage getImage() {
		return shopkeeper;
	}
}
