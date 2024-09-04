package frog.weapons;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;


/**
 * A rifle class that extends Projectile Weapon
 * @author Mikaela Kwan
 *
 */

public class Rifle extends ProjectileWeapon{
	
	//Fields
	public static final double RIFLE_DAMAGE = 25.0;
	public static final double RIFLE_RANGE = 500.0;
	public static final double RIFLE_SPEED = 15.0;
	private PImage rifleImage;
	
	//Constructors
	public Rifle(PApplet marker) {
		super(RIFLE_DAMAGE, RIFLE_RANGE, RIFLE_SPEED);
		rifleImage = marker.loadImage("resources/rifle.png");
	}
	
	//Methods
	/**
	 * Draws this rifle.
	 * @param draw, the PApplet to draw the rifle on.
	 */
	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.image(rifleImage, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Shoots this rifle to the point (x, y)
	 * @param x, the x coordinate of the target
	 * @param y, the y coordinate of the target
	 */
	public void shoot(int x, int y) {
		
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Rifle");
		return data;
	}
}
