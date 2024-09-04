package frog.entities;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents an abstract entity which all other entities will extend, detects collisions using awt rectangles.
 * @author Jeremy Mills
 *
 */
public abstract class Entity {
	//Fields
	protected double x, y, width, height;
	protected double vX, vY;
	protected double health;
	protected double speedMultiplyer, strengthMultiplyer;
	protected PImage image;
	protected double maxHealth;
	
	//Constructors
	public Entity(double x, double y, double width, double height, double health) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.health = health;
		this.maxHealth = health;
		vX = 0;
		vY = 0;
	}
	
	public Entity(Map<String, Object> map) {
		this.x = (double) map.get("x");
		this.y = (double) map.get("y");
		this.width = (double) map.get("width");
		this.height = (double) map.get("height");
		this.vX = (double) map.get("vX");
		this.vY = (double) map.get("vY");
		this.health = (double) map.get("health");
		this.speedMultiplyer = (double) map.get("speedMultiplyer");
		this.strengthMultiplyer = (double) map.get("strengthMultiplyer");
		this.maxHealth = (double) map.get("maxHealth");
	}
	
	//Methods
	/**
	 * Detects if this entity is touching a rectangle belonging to another entity/item/projectile.
	 * @param other, the rectangle hitbox of another entity/item/projectile.
	 */
	public boolean isTouching(Rectangle other) {
		if(Math.abs(x - other.x) > this.width + other.width || Math.abs(y - other.y) > this.height + other.height) {
			return false;
		}
		Rectangle r1 = new Rectangle((int)x, (int)y, (int)width, (int)height);
		if(r1.contains(new Point((int)other.getX(), (int)other.getY()))) {
			return true;
		}
		else if(r1.contains(new Point((int)(other.getX() + other.getWidth()), (int)other.getY()))) {
			return true;
		}
		else if(r1.contains(new Point((int)other.getX(), (int)(other.getY() + other.getHeight())))) {
			return true;
		}
		else if(r1.contains(new Point((int)(other.getX() + other.getWidth()), (int)(other.getY() + other.getHeight())))) {
			return true;
		}
		
		if(other.contains(new Point((int)x, (int)y))) {
			return true;
		}
		else if(other.contains(new Point((int)(x + width), (int)y))) {
			return true;
		}
		else if(other.contains(new Point((int)x, (int)(y + height)))) {
			return true;
		}
		else if(other.contains(new Point((int)(x + width), (int)(y + height)))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Moves the entity based on its velocity.
	 */
	public void move() {
		x += vX;
		y += vY;
	}
	
	/**
	 * Moves the entity by the given amount
	 * @param x Amount to move the entity by in the x-direction
	 * @param y Amount to move the entity by in the y-direction
	 */
	public void moveBy(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Changes the entity's velocity by accelerating.
	 * @param aX, the value to add to the x velocity.
	 * @param aY, the value to add to the y velocity
	 */
	public void accelerate(double aX, double aY) {
		vX += aX;
		vY += aY;
	}
	
	/**
	 * Moves the entity to the point (x, y).
	 * @param x, The x coordinate of the new location.
	 * @param y, The y coordinate of the new location.
	 */
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Deals damage to the entity by a specific amount.
	 * @param amount, the amount of health that is being lost.
	 */
	public void damage(double amount) {
		
	}
	
	/**
	 * Draws the Entity's image
	 * @param marker, the PApplet to draw the entity on.
	 */
	public void draw(PApplet marker) {
		
	}
	
	/**
	 * Shifts X by the given amount
	 * @param shiftX Amount to shift X by
	 */
	protected void shiftX(double shiftX) {
		//if(shiftX == Integer.MAX_VALUE)
		//	return;
		
		shiftX += 1.0;
		if(vX > 0)
			shiftX = -shiftX;
		moveBy(shiftX, 0);
		vX = 0.0;
	}
	
	/**
	 * Shifts Y by the given amount
	 * @param shiftY Amount to shift by
	 */
	protected void shiftY(double shiftY) {
		
		shiftY += 1.0;
		if(vY > 0)
			shiftY = -shiftY;
		moveBy(0, shiftY);
		vY = 0.0;
	}
	
	//Getters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double amount) {
		health = amount;
	}
	public double getSpeed() {
		return speedMultiplyer;
	}
	public double getStrength() {
		return strengthMultiplyer;
	}
	public void increaseSpeed(double amount) {
		speedMultiplyer += amount;
	}
	public void increaseStrength(double amount) {
		strengthMultiplyer += amount;
	}
	
	/**
	 * Returns this Entity's unique fields as a HashMap with key/value pairs
	 * @return HashMap containing all unique fields of this Entity
	 */
	public Map<String, Object> asMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("x", x);
		data.put("y", y);
		data.put("width", width);
		data.put("height", height);
		data.put("vX", vX);
		data.put("vY", vY);
		data.put("health", health);
		data.put("speedMultiplyer", speedMultiplyer);
		data.put("strengthMultiplyer", strengthMultiplyer);
		data.put("maxHealth", maxHealth);
		return data;
	}
}
