package frog.screen;

import java.awt.Point;
import java.util.ArrayList;

import frog.DrawingSurface;
import frog.util.Button;
import frog.util.ButtonListener;
import processing.core.PApplet;

/**
 * Abstract screen. Subclasses will be used by PApplets to draw navigation screens within the program.
 * @author Justin Hwang
 *
 */
public abstract class Screen implements ButtonListener {
	
	//Fields
	public final int DRAWING_WIDTH = 800;
	public final int DRAWING_HEIGHT = 600;
	protected DrawingSurface surface;
	protected ArrayList<Button> buttons;
	
	//Constructor
	public Screen(DrawingSurface surface) {
		this.surface = surface;
		buttons = new ArrayList<Button>();
	}
	
	//Methods
	/**
	 * The statements in the setup() function execute once when the program begins
	 */
	public void setup() {
		
	}
	
	/**
	 * The statements in draw() are executed until the 
	 * program is stopped. Each statement is executed in 
	 * sequence and after the last line is read, the first 
	 * line is executed again.
	 */
	public void draw() {
		
	}
	
	/**
	 * Returns the DrawingSurface that this Screen exists inside of
	 * @return DrawingSurface object
	 */
	public DrawingSurface getSurface() {
		return this.surface;
	}
	
	/**
	 * Updates the Buttons
	 * @param mouseX x-coordinate of the mouse, using actual coords, not assumed
	 * @param mouseY y-coordinate of the mouse, using actual coords, not assumed
	 * @param mousePressed True if mouse is currently pressed down, false if not
	 */
	public void updateButtons(int mouseX, int mouseY, boolean mousePressed) {
		for(Button b : buttons)
			b.update(mouseX, mouseY, mousePressed);
	}
	
	public void updateButtons(Point mouseCoords, boolean mousePressed) {
		updateButtons(mouseCoords.x, mouseCoords.y, mousePressed);
	}
	
	public void drawButtons(PApplet surface) {
		for(Button b : buttons)
			b.draw(surface);
	}
	
	/**
	 * 
	 */
	public void mousePressed() {
		
	}
	
	/**
	 * 
	 */
	public void mouseMoved() {
		
	}
	
	/**
	 * 
	 */
	public void mouseDragged() {
		
	}
	
	/**
	 * 
	 */
	public void mouseReleased() {
		
	}
	
	public void keyPressed() {
		
	}
}
