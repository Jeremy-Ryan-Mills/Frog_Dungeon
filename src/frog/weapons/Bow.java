package frog.weapons;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A bow class that extends Projectile Weapon
 * @author Mikaela Kwan
 *
 */
public class Bow extends ProjectileWeapon {
	
	//Fields
	public static final double BOW_DAMAGE = 9.0;
	public static final double BOW_RANGE = 300.0;
	public static final double BOW_SPEED = 3;
	private PImage bowImage;
	
	//Constructors
	public Bow(PApplet marker) {
		super(BOW_DAMAGE, BOW_RANGE, BOW_SPEED);
		if(marker != null) {
			bowImage = marker.loadImage("resources/bow.png");
		}
	}
		
	//Methods
	/**
	 * Draws this bow.
	 * @param draw, the PApplet to draw the bow on.
	 */
	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.image(bowImage, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Shoots this bow to the point (x, y)
	 * @param x, the x coordinate of the target
	 * @param y, the y coordinate of the target
	 */
	public void shoot(int startX, int startY, int endX, int endY) {
		super.shoot(startX, startY, endX, endY);
		
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Bow");
		return data;
	}
}
