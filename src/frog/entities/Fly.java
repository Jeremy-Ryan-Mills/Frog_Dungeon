package frog.entities;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a lower level fly monster that extends the Monster class.
 * @author Mikaela Kwan
 *
 */
public class Fly extends Monster{

	//Fields
	public static final double FLY_DAMAGE = 10.0;
	public static final double FLY_RANGE = 500.0;
	public static final double FLY_SPEED = 2.0;
	public static final int FLY_COIN_VALUE = 5;
	private PImage flyImage;
	private PImage flyImage1;
	private int ticks;
	
	//Constructors
	public Fly(double x, double y, double width, double height, double health, PApplet marker) {
		super(x, y, width, height, health, FLY_DAMAGE, FLY_RANGE, FLY_SPEED, FLY_COIN_VALUE);
		flyImage = marker.loadImage("resources/fly.png");
		flyImage1 = marker.loadImage("resources/fly1.png");
		ticks = 0;
	}	

	
	public Fly(Map<String, Object> map, PApplet marker) {
		super(map, FLY_DAMAGE, FLY_RANGE, FLY_SPEED, FLY_COIN_VALUE);
		flyImage = marker.loadImage("resources/fly.png");
		flyImage1 = marker.loadImage("resources/fly1.png");
		ticks = 0;
	}
	
	//Methods
	public void draw(PApplet marker) {
		ticks++;
		marker.pushStyle();
		
		if(ticks%40 < 20)
			marker.image(flyImage, (float)x, (float)y, (float)width, (float)height);
		else 
			marker.image(flyImage1, (float)x, (float)y, (float)width, (float)height);
		
		
		marker.noFill();
		marker.stroke(228, 74, 74);
		//marker.rect((float) x, (float) y, (float) width, (float) height);
		marker.popStyle();
		
		super.draw(marker);
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Fly");
		return data;
	}
}
