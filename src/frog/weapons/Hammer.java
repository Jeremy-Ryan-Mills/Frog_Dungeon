package frog.weapons;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A hammer class, that extends Melee Weapon, and deals a constant damage. To use, give the player this weapon in their meleeweapon field.
 * @author Jeremy Mills
 *
 */
public class Hammer extends MeleeWeapon{

	//Fields
	public static final double HAMMER_DAMAGE = 25.0;
	private PImage hammerImage;
	
	//Constructors
	public Hammer(PApplet marker) {
		super(HAMMER_DAMAGE);
		hammerImage = marker.loadImage("resources/hammer.png");
	}
	
	//Methods
	/**
	 * Draws this Hammer.
	 * @param marker, the PApplet to draw the Hammer on. 
	 */
	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.image(hammerImage, (float)x, (float)y, (float)width, (float)height);
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Hammer");
		return data;
	}
}
