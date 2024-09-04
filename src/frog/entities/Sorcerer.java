package frog.entities;

import java.awt.Point;

import frog.DrawingSurface;
import frog.screen.FrogDungeon;
import frog.screen.Screen;
import frog.weapons.Bow;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represents the Sorcerer final boss. Extends Monster.
 * @author Mikaela Kwan
 *
 */
public class Sorcerer extends Monster {

	//Fields
	public static final double SORCERER_DAMAGE = 20.0;
	public static final double SORCERER_RANGE = 1000.0;
	public static final double SORCERER_SPEED = 0.5;
	public static final int SORCERER_COIN_VALUE = 15;
	private PImage sorcerer;
	private PImage sorcerer1;
	private int ticks;
	
	private Bow bow;
	
	//Constructors
	public Sorcerer(double x, double y, double width, double height, double health) {
		super(x, y, width, height, health, SORCERER_DAMAGE, SORCERER_RANGE, SORCERER_SPEED, SORCERER_COIN_VALUE);
		bow = new Bow(null);
		ticks = 0;
		
	}
	
	//Methods
	public void draw(PApplet marker) {
		if(sorcerer == null)
			sorcerer = marker.loadImage("resources/sorcerer.png");
		if (sorcerer1 == null)
			sorcerer1 = marker.loadImage("resources/sorcerer1.png");
		ticks++;
		
		if(ticks%40 < 20)
			marker.image(sorcerer1, (float)x, (float)y, (float)width, (float)height);
		else 
			marker.image(sorcerer, (float)x, (float)y, (float)width, (float)height);
		//marker.fill(255);
		//marker.ellipseMode(marker.CORNER);
		//marker.ellipse((float)x, (float)y, (float)width, (float)height);
		//marker.image(sorcerer, (float) x, (float) y, (float) width, (float) height);
	}
	
	/**
	 * Shoots its projectiles at the player
	 * @param x, the target x
	 * @param y, the target y
	 * @param screen, the screen where the boss is
	 */
	public void shootRangedWeapon(int x, int y, Screen screen) {
		DrawingSurface surface = screen.getSurface();
		int offsetX = 0;
		int offsetY = 0;
		if(screen instanceof FrogDungeon) {
			offsetX = (int) ((float) this.getX() + (float) this.getWidth()/2 - 400); //this is the X amount the screen is shifted
			offsetY = (int) ((float) this.getY() + (float) this.getHeight()/2 - 300); //this is the Y amount the screen is shifted
		}
		
		offsetY += 20; //idk why but mouseY includes the height of the window
		Point actualMousePoint = surface.assumedCoordinatesToActual(x, y);
		bow.shoot((int)this.getX() + (int) (this.width/2), (int)this.getY() + (int) (this.height/2), actualMousePoint.x + offsetX, actualMousePoint.y + offsetY);
	}
	
	public Bow getBow() {
		return bow;
	}
}
