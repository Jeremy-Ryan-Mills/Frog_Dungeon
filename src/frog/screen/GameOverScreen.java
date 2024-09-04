package frog.screen;

import frog.util.Button;
import processing.core.PImage;
import frog.DrawingSurface;

/**
 * Screen that appears when player dies
 * @author Justin Hwang
 *
 */
public class GameOverScreen extends Screen {
	//Fields
	private Button menuButton;
	private PImage gameOverImage;
	
	//Constructors
	/**
	 * Constructs a new MenuScreen
	 * @param surface The DrawingSurface that this MenuScreen exists inside of
	 */
	public GameOverScreen(DrawingSurface surface) {
		super(surface);
		
		//frog = new PImage();
		
		menuButton = new Button(300, 375, 200, 50);
		menuButton.setText("Main Menu");
		menuButton.setButtonListener(this);
		buttons.add(menuButton);
		
		gameOverImage = surface.loadImage("resources/gameOver.png");

	}
	
	public void draw() {

		surface.background(28, 29, 30);
		surface.pushStyle();
		//surface.textAlign(DrawingSurface.CENTER, DrawingSurface.CENTER);

		//surface.text("FROG DUNGEON", 350, 50);
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
		surface.pushMatrix();
		surface.translate(400, 200);
		surface.scale(0.8f, 0.8f);
		surface.image(gameOverImage, 0 - gameOverImage.width/2, 0 - gameOverImage.height/2);
		surface.popMatrix();

		surface.popStyle();
	}

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(menuButton))
			surface.switchScreen(DrawingSurface.MENU_SCREEN);
	}
}
