package frog.entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import frog.misc.*;
import frog.screen.FrogDungeon;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * A monster class where all monsters extend this class.
 * @author Jeremy Mills
 *
 */
public abstract class Monster extends Entity {

	//Fields
	protected double damage;
	protected Item dropItem;
	protected double range;
	protected double speed;
	protected int coinValue;
	
	//Constructors
	public Monster(double x, double y, double width, double height, double health, double damage, double range, double speed, int coinValue) {
		super(x, y, width, height, health);
		this.damage = damage;
		this.range = range;
		this.speed = speed;
		this.coinValue = coinValue;
		
		int i = (int)(Math.random()*10);
		//System.out.println(i);
		if(i == 1) {
			dropItem = new HealthPotion(this.x, this.y, 50, 50);
		}
		else if(i == 2) {
			dropItem = new StrengthPotion(this.x, this.y, 50, 50);
		}
		else if(i == 3) {
			dropItem = new SpeedPotion(this.x, this.y, 50, 50);
		}
		else {
			dropItem = null;
		}
		//System.out.println(dropItem);
		
	}
	
	public Monster(Map<String, Object> map, double damage, double range, double speed, int coinValue) {
		super(map);
		this.damage = damage;
		this.range = range;
		this.speed = speed;
		this.coinValue = coinValue;
		
		//int i = (int)(Math.random()*10);
		//Item item = new Item(map.get("dropItem."));
		//Object rawItemType = map.get("dropItem.type");
		//if(rawItemType == null || rawItemType.equals(""))
		//	return;
		Object uncastItemMap = map.get("dropItem");
		if(!(uncastItemMap instanceof HashMap))
			return;
		HashMap<String, Object> itemMap = (HashMap<String, Object>) uncastItemMap;
		String itemType = (String) itemMap.get("type");
		//System.out.println(i);
		if(itemType.equals("HealthPotion")) {
			dropItem = new HealthPotion(this.x, this.y, 50, 50);
		}
		else if(itemType.equals("StrengthPotion")) {
			dropItem = new StrengthPotion(this.x, this.y, 50, 50);
		}
		else if(itemType.equals("SpeedPotion")) {
			dropItem = new SpeedPotion(this.x, this.y, 50, 50);
		}
		else {
			dropItem = null;
		}
		//System.out.println(dropItem);
		
	}
		
	//Methods
	/**
	 * Attacks the Frog given using its damage field.
	 * @param player, the Frog that is going to take the damage.
	 */
	public void attackPlayer(Frog player) {
		
	}
	
	//Moves towards the player
	public void move(ArrayList<Wall> walls, ArrayList<Monster> monsters, double frogX, double frogY) {
		int targetX = (int)frogX;
		int targetY = (int)frogY;

        //calculating the angle
        double changeX = targetX - x;
        double changeY = y - targetY;

        double hyp = Math.sqrt(changeX * changeX + changeY * changeY);
        
        if(hyp > range)
        	return;

        if(hyp != 0 && hyp < range) {
        	
            double refAngle = Math.asin(changeY/hyp);
            //double cosAngle = Math.acos(changeX/hyp);

            //if(cosAngle > Math.PI/2) {
            //    refAngle = Math.PI - refAngle;
            //}
            if(changeX < 0)
            	refAngle = Math.PI - refAngle;

            vX = (speed * Math.cos(refAngle));
            vY = -(speed * Math.sin(refAngle));
        }

        
        //COLLISIONS
        
		//makes a list of all rectangles that make up the walls
        
       
		ArrayList<Rectangle> wallRectangles = new ArrayList<Rectangle>();
			
		if(walls!=null) {
			for(Wall wall : walls)
				wallRectangles.addAll(wall.getRectangles());
		}
		if(monsters != null) {
			for(Monster m : monsters)
				if(!m.equals(this))
					wallRectangles.add(new Rectangle((int) m.getX(), (int) m.getY(), (int) m.getWidth(), (int) m.getHeight()));
			
		}
		super.move();
		
		
		if (walls != null || monsters != null) {

			Rectangle rectTouched = null;

			double shiftX = Integer.MAX_VALUE;
			double shiftY = Integer.MAX_VALUE;
			for (Rectangle r : wallRectangles) {
				if (isTouching(r)) {
					rectTouched = r;
					//System.out.println("object at " + this.getX() + ", " + this.getY() + " touched rectangle (" + r.getX() + ", " + r.getY() + ", " + r.getWidth() + ", " + r.getHeight());
					double thisLeft = this.x;
					double thisRight = this.x + this.width;
					double rectLeft = r.x;
					double rectRight = r.x + r.width;

					/*if (Math.min(thisRight - rectLeft, rectRight - thisLeft) < shiftX) {
						shiftX = Math.min(thisRight - rectLeft, rectRight - thisLeft);
					}*/
					shiftX = Math.min(thisRight - rectLeft, rectRight - thisLeft);

					double thisTop = this.y;
					double thisBottom = this.y + this.height;
					double rectTop = r.y;
					double rectBottom = r.y + r.height;

					/*if (Math.min(thisBottom - rectTop, rectBottom - thisTop) < shiftY) {
						shiftY = Math.min(thisBottom - rectTop, rectBottom - thisTop);
					}*/

					shiftY = Math.min(thisBottom - rectTop, rectBottom - thisTop);
					
					
					//System.out.println("shiftX = " + shiftX + ", shiftY = " + shiftY);
					if (shiftX < shiftY && vX != 0) {
						shiftX(shiftX);
						if (isTouching(rectTouched)) {
							shiftY(shiftY);
						}
					} else if (shiftY < shiftX && vY != 0) {
						shiftY(shiftY);
						if (isTouching(rectTouched)) {
							shiftX(shiftX);
						}
					}
				}
			}

			
			if(dropItem != null) {
	        	dropItem.setX(this.x);
	        	dropItem.setY(this.y);
	        }
			
		}
    }
		//System.out.println(vX + " " + vY);

	
	public Item getItem() {
		return dropItem;
	}
	public double getDamage() {
		return damage;
	}
	public int getCoinValue() {
		return coinValue;
	}
	
	public void draw(PApplet marker) {
		marker.pushStyle();
		marker.fill(228, 74, 74);
		int maxHealthBarWidth = 80;
		int width = (int) ( maxHealthBarWidth * (health/maxHealth) );
		marker.rect((float) (x + this.width/2 - maxHealthBarWidth/2), (int) (y + height), width, 10, 3);
		marker.popStyle();
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = super.asMap();

		if(dropItem != null)
			data.put("dropItem", dropItem.asMap());
		return data;
	}
}
