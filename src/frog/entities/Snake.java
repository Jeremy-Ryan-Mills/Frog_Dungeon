package frog.entities;

import java.util.Map;

import frog.DrawingSurface;
import frog.screen.FrogDungeon;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents a higher level snake monster that extends the Monster class.
 * @author Mikaela Kwan
 *
 */
public class Snake extends Monster{

	//Fields
	public static final double SNAKE_DAMAGE = 15.0;
	public static final double SNAKE_RANGE = 500.0;
	public static final double SNAKE_SPEED = 1.5;
	public static final int SNAKE_COIN_VALUE = 10;
	private static PImage snakeImage;
	private static PImage snakeLeftImage;
	private int ticks;
	
	//Constructors
	public Snake(double x, double y, double width, double height, double health, PApplet marker) {
		super(x, y, width, height, health, SNAKE_DAMAGE, SNAKE_RANGE, SNAKE_SPEED, SNAKE_COIN_VALUE);
		if(snakeImage == null)
			snakeImage = marker.loadImage("resources/snake.png");
		if(snakeLeftImage == null)
			snakeLeftImage = marker.loadImage("resources/snakeLeft.png");
		ticks = 0;
	}
	
	public Snake(Map<String, Object> map, PApplet marker) {
		super(map, SNAKE_DAMAGE, SNAKE_RANGE, SNAKE_SPEED, SNAKE_COIN_VALUE);
		if(snakeImage == null)
			snakeImage = marker.loadImage("resources/snake.png");
		if(snakeLeftImage == null)
			snakeLeftImage = marker.loadImage("resources/snakeLeft.png");
		ticks = 0;
	}
	
	//Methods
	public void draw(PApplet marker) {
		ticks++;
		marker.pushStyle();
		
		if(ticks%40 < 20)
			marker.image(snakeImage, (float)x, (float)y, (float)width, (float)height);
		else 
			marker.image(snakeLeftImage, (float)x, (float)y, (float)width, (float)height);
		
		
		
		
		//marker.fill(0, 0, 256);
		//marker.ellipse((float)x + (float)width/2, (float)y + (float)height/2, (float)width, (float)height);
		
		marker.noFill();
		marker.stroke(228, 74, 74);
		//marker.rect((float) x, (float) y, (float) width, (float) height);
		marker.popStyle();
		
		super.draw(marker);
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();
		data.put("type", "Snake");
		return data;
	}
}
