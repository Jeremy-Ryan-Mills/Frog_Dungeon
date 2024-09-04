package frog.screen;

import frog.util.Button;
import processing.core.PImage;

import java.awt.Color;
import java.io.File;

import frog.DrawingSurface;

/**
 * Screen that appears, that loads a list of save files.
 * @author Justin Hwang
 *
 */
public class LoadGameScreen extends Screen {
	//Fields
	Button backButton;
	
	//Constructors
	/**
	 * Constructs a new MenuScreen
	 * @param surface The DrawingSurface that this MenuScreen exists inside of
	 */
	public LoadGameScreen(DrawingSurface surface) {
		super(surface);
		backButton = new Button(5, 5, 150, 50);
		backButton.setText("Back");
		backButton.setButtonListener(this);
		buttons.add(backButton);
		
		File saveDirectory = new File("saves");
		File[] contents = saveDirectory.listFiles();
		//if (contents != null)
		for(int i = 0; i < contents.length; i++) {
			Button button = new Button(100, 150 + 50 * i, 600, 45);
			String fileName = contents[i].getName();
			button.setText(fileName.substring(0, fileName.length() - 4));
			button.setButtonListener(this);
			//button.setTextSize(25);
			buttons.add(button);
		}

	}
	
	public void draw() {
		surface.background(28, 29, 30);
		surface.pushStyle();
		surface.textAlign(DrawingSurface.CENTER);
		surface.textSize(50);
		surface.fill(255);
		surface.text("Select Save File...", 400, 100);
		surface.textSize(20);
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);

		surface.popStyle();
	}

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(backButton)) {
			surface.switchScreen(surface.MENU_SCREEN);
			return;
		}
		surface.loadGameFromFile("saves/" + button.getText() + ".yml");
		surface.switchScreen(surface.GAME_SCREEN);
	}
}
