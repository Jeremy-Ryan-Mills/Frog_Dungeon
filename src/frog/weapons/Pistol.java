package frog.weapons;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A pistol class that extends Projectile Weapon
 * @author Mikaela Kwan
 *
 */

public class Pistol extends ProjectileWeapon{

	//Fields
	public static final double PISTOL_DAMAGE = 15.0;
	public static final double PISTOL_RANGE = 400.0;
	public static final double PISTOL_SPEED = 6;
	private PImage pistolImage;
	
	//Constructors
	public Pistol(PApplet marker) {
		super(PISTOL_DAMAGE, PISTOL_RANGE, PISTOL_SPEED);
		pistolImage = marker.loadImage("resources/pistol.png");
	}
	
	//Methods
	/**
	 * Draws this pistol.
	 * @param draw, the PApplet to draw the pistol on.
	 */
	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.image(pistolImage, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Shoots this pistol to the point (x, y)
	 * @param x, the x coordinate of the target
	 * @param y, the y coordinate of the target
	 */
	public void shoot(int x, int y) {
		
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Pistol");
		return data;
	}
}
