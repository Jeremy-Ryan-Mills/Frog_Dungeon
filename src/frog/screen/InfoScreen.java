package frog.screen;

import frog.util.Button;
import processing.core.PImage;
import frog.DrawingSurface;

/**
 * Represents the "How to Play" screen that can be accessed from the MenuScreen. Displays the controls.
 * @author Mikaela Kwan
 *
 */
public class InfoScreen extends Screen {
	//Fields
	private Button returnButton;
	private PImage gorf;
	private PImage potion1;
	private PImage potion2;
	private PImage potion3;
	
	//Constructors
	/**
	 * Constructs a new InfoScreen
	 * @param surface The DrawingSurface that this InfoScreen exists inside of
	 */
	public InfoScreen(DrawingSurface surface) {
		super(surface);
		
		gorf = new PImage();
		potion1 = new PImage();
		potion2 = new PImage();
		potion3 = new PImage();
		
		returnButton = new Button(20, 20, 200, 50);
		returnButton.setText("Return to Main Menu");
		returnButton.setButtonListener(this);
		buttons.add(returnButton);
		
	}
	
	//Methods
	public void draw() {
		gorf = surface.loadImage("resources/gorf.png");
		potion1 = surface.loadImage("resources/healthpotion.png");
		potion2 = surface.loadImage("resources/speedpotion.png");
		potion3 = surface.loadImage("resources/strengthpotion.png");
		
		surface.background(28, 29, 30);
		surface.pushStyle();
	//    surface.textAlign(DrawingSurface.CENTER);
		surface.textSize(15);
		surface.text("HOW TO PLAY", 345, 50);
		surface.text("Defeat the evil Frog Sorcerer's army of monsters and navigate his labyrinth to save Princess Gorf!", 50, 250);
		surface.text("WASD to move", 350, 300);
		surface.text("Left click to melee attack, right click to projectile attack", 200, 325);
		surface.text("Press E to get items as you defeat monsters", 250, 350);
		surface.text("Use coins to buy new weapons at the Shopkeeper", 230, 440);
		surface.text("Once you are ready, search for and take on the final boss!", 200, 475);
		surface.textSize(13);
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
		surface.image(gorf, 325, 70, 150, 150);
		surface.image(potion1, 280, 365, 50, 50);
		surface.image(potion2, 370, 365, 50, 50);
		surface.image(potion3, 460, 365, 50, 50);
		
		surface.popStyle();
	}

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(returnButton)) {
			surface.switchScreen(surface.MENU_SCREEN);
		}
	}
}
