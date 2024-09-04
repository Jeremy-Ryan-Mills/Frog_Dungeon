package frog.misc;

import java.util.Map;

import frog.screen.FrogDungeon;
import processing.core.PApplet;

/**
 * Represents a SpeedPotion that increases the speed of the player by a percentage when the player touches the potion.
 * @author Jeremy Mills
 *
 */
public class SpeedPotion extends Item{
	
	//Fields
	public static final double SPEED_BUFF = 0.05;
	
	//Constructors
	public SpeedPotion(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	public SpeedPotion(Map<String, Object> map) {
		super(map);
	}

	//Methods
	public void draw(PApplet marker) {
		if(image == null) {
			image = marker.loadImage("resources/speedpotion.png");
		}
		marker.image(image, (float)x, (float)y, (float)width, (float)height);
	}

	public void doAction(FrogDungeon x) {
		x.getFrog().increaseSpeed(SPEED_BUFF);
		if(x.getFrog().getSpeed() > 1.5) {
			x.getFrog().increaseSpeed(1.5-x.getFrog().getSpeed());
		}
		if (x.getFrog().getSpeed() < 1.5) {
			x.setMessage("+" + (int)(SpeedPotion.SPEED_BUFF*100) + "% Speed!");
		} else {
			x.setMessage("Max speed reached!");
		}
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "SpeedPotion");
		return data;
	}

}
