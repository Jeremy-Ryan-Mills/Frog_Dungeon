package frog.misc;

import java.util.Map;

import frog.screen.FrogDungeon;
import processing.core.PApplet;

/**
 * Represents a StrengthPotion that increases the player's strength by a percentage when the player touches it.
 * @author Jeremy Mills
 *
 */
public class StrengthPotion extends Item{

	//Fields
	public static final double STRENGTH_BUFF = 0.2;
	
	//Constructors
	public StrengthPotion(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
	
	public StrengthPotion(Map<String, Object> map) {
		super(map);
	}
	
	//Methods
	public void draw(PApplet marker) {
		if(image == null)
			image = marker.loadImage("resources/strengthpotion.png");
		marker.image(image, (float)x, (float)y, (float)width, (float)height);
	}
	
	public void doAction(FrogDungeon x) {
		x.getFrog().increaseStrength(STRENGTH_BUFF);
		if(x.getFrog().getStrength() > 2) {
			x.getFrog().increaseStrength(2-x.getFrog().getStrength());
		}
		if(x.getFrog().getStrength() < 2) {
			x.setMessage("+" + (int)(StrengthPotion.STRENGTH_BUFF*100) + "% Strength!");
		} else {
			x.setMessage("Max strength reached!");
		}
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "StrengthPotion");
		return data;
	}

}
