package frog.misc;

import java.util.HashMap;
import java.util.Map;

import frog.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the Boss Tile, where if you interact with it, it asks you if you want to go to the boss room.
 * @author Jeremy Mills
 *
 */
public class BossTile {

	//Fields
	private double x, y;
	private PImage trapdoor;
	
	//Constructor
	public BossTile(double x, double y, PApplet marker) {
		this.x = x;
		this.y = y;
		trapdoor = marker.loadImage("resources/trapdoor.png");
	}
	
	public BossTile(Map<String, Object> map, PApplet marker) {
		this.x = (double) map.get("x");
		this.y = (double) map.get("y");
		trapdoor = marker.loadImage("resources/trapdoor.png");
	}
	
	//Methods
	/**
	 * Draws the BossTile on the Grid
	 * @param marker, the PApplet to draw the bosstile on.
	 */
	public void draw(PApplet marker) {
		marker.fill(256, 0, 0);
		marker.image(trapdoor, (float)x, (float)y, 50f, 50f);
		//marker.rect((float)x, (float)y, 50f, 50f);
	}
	
	/**
	 * Changes the screen to the Boss GUI.
	 * @param surface, the DrawingSurface to switchscreens on.
	 */
	public void changeScreen(DrawingSurface surface) {
		surface.switchScreen(surface.BOSS_GUI);
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	/**
	 * Determines if a point is inside the tile of the bosstile.
	 * @param x, the x of the point tested.
	 * @param y, the y of the point tested.
	 * @return boolean, true if it is inside the tile, and false if not.
	 */
	public boolean isInsideTile(double x, double y) {
		if (this.x <= x && this.y <= y && (this.x + 50f) > x && (this.y + 50f) > y) 
			return true;
		
		return false;
	}
	
	public PImage getImage() {
		return trapdoor;
	}
	
	public Map<String, Object> asMap() {
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("x", x);
		 data.put("y", y);
		 return data;
	}
	
	
}
