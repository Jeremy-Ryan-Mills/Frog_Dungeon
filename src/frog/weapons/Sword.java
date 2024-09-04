package frog.weapons;

import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A sword class, that extends Melee Weapon, and deals a constant damage. To use, give the player this weapon in their meleeweapon field.
 * @author Jeremy Mills
 *
 */
public class Sword extends MeleeWeapon{

	//Fields
	public static final double SWORD_DAMAGE = 15.0;
	private PImage swordImage;
	
	//Constructors
	public Sword(PApplet marker) {
		super(SWORD_DAMAGE);
		swordImage = marker.loadImage("resources/sword.png");
	}
	
	
	
	//Methods
	/**
	 * Draws this Sword.
	 * @param marker, the PApplet to draw the Sword on. 
	 */
	public void draw(PApplet marker, double x, double y, double width, double height) {
		marker.image(swordImage, (float)x, (float)y, (float)width, (float)height);
	}
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Sword");
		return data;
	}
}
