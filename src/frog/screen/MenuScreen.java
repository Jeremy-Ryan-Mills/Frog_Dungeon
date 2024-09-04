package frog.screen;

import frog.util.Button;
import processing.core.PImage;

import java.awt.Color;

import frog.DrawingSurface;

/**
 * Default screen that appears when the program is run. Displays a start button and a how to play button.
 * @author Justin Hwang
 *
 */
public class MenuScreen extends Screen {
	//Fields
	private Button startButton;
	private Button infoButton;
	private Button resumeButton;
	private PImage title;
	boolean resumeIsValid;
	
	//Constructors
	/**
	 * Constructs a new MenuScreen
	 * @param surface The DrawingSurface that this MenuScreen exists inside of
	 */
	public MenuScreen(DrawingSurface surface) {
		super(surface);
		resumeIsValid = false;
		
		//frog = new PImage();
		
		startButton = new Button(300, 350, 200, 50);
		startButton.setText("New Game");
		startButton.setButtonListener(this);
		buttons.add(startButton);
		
		resumeButton = new Button(300, 425, 200, 50);
		resumeButton.setText("Load Game");
		resumeButton.setButtonListener(this);
		buttons.add(resumeButton);
		
		infoButton = new Button(300, 500, 200, 50);
		infoButton.setText("How To Play");
		infoButton.setButtonListener(this);
		buttons.add(infoButton);

	}
	
	public void draw() {
		if(title == null)
			title = surface.loadImage("resources/title.png");

		surface.background(28, 29, 30);
		surface.pushStyle();
		//surface.textAlign(DrawingSurface.CENTER, DrawingSurface.CENTER);

		//surface.text("FROG DUNGEON", 350, 50);
		
		//resumeIsValid = ((FrogDungeon)surface.getScreen(DrawingSurface.GAME_SCREEN)).getFrog().getHealth() > 0;
		resumeIsValid = true;
		if(!resumeIsValid) {
			resumeButton.setMainColor(Color.LIGHT_GRAY);
			resumeButton.setHoveredColor(Color.LIGHT_GRAY);
			resumeButton.setPressedColor(Color.LIGHT_GRAY);
		} else {
			resumeButton.setMainColor(new Color(139, 182, 47));
			resumeButton.setHoveredColor(new Color(72, 124, 56));
			resumeButton.setPressedColor(new Color(247, 229, 215));
		}
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
		surface.image(title, 100, 10);

		surface.popStyle();
	}

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(startButton)) {
			surface.resetGame();
			surface.switchScreen(surface.GAME_SCREEN);
		}
		else if(button.equals(infoButton)) {
			surface.switchScreen(surface.INFO_SCREEN);
		} else if(button.equals(resumeButton) && resumeIsValid) {
			//surface.switchScreen(surface.GAME_SCREEN);
			//surface.loadGameFromFile("saves/untitled.yml");
			//surface.switchScreen(surface.GAME_SCREEN);
			surface.reloadSaveFiles();
			surface.switchScreen(surface.LOAD_GAME_SCREEN);
		}
	}
}
