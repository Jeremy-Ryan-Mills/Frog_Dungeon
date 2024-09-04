package frog.screen;

import frog.util.Button;

import java.awt.Color;
import java.awt.event.KeyEvent;

import frog.DrawingSurface;

/**
 * Represents the Screen that appears while the game is Paused.
 * @author Mikaela Kwan
 *
 */
public class PauseScreen extends Screen {
	
	//Fields
	private Button resumeButton;
	private Button saveButton;
	private Button exitButton;
	private boolean isInputtingText;
	private String saveDestination;
	private boolean saveOnNextFrame;
	private String message;
	private int messageTimer;
	private int ticks;
	
	// Constructors
	/**
	 * Constructs a new PauseScreen
	 * @param surface The DrawingSurface that this PauseScreen exists inside of
	 */
	public PauseScreen(DrawingSurface surface) {
		super(surface);
		
		resumeButton = new Button(300, 100, 200, 150);
		resumeButton.setText("Resume");
		resumeButton.setButtonListener(this);
		buttons.add(resumeButton);
		
		saveButton = new Button(300, 250, 200, 150);
		saveButton.setText("Save Game");
		saveButton.setButtonListener(this);
		buttons.add(saveButton);
		
		exitButton = new Button(300, 400, 200, 150);
		exitButton.setText("Exit");
		exitButton.setButtonListener(this);
		buttons.add(exitButton);

		isInputtingText = false;
		saveDestination = "untitled";
		saveOnNextFrame = false;
		message = "";
		messageTimer = 0;
		ticks = 0;
		
		//TODO: Create resume, save and exit buttons, and add them to "buttons" arraylist inherited from Screen superclass
	}
	
	public void draw() {
		ticks++;
		surface.background(28, 29, 30);
		surface.pushStyle();
	//	surface.textAlign(DrawingSurface.CENTER, DrawingSurface.CENTER);
		surface.text("GAME PAUSED", 355, 50);
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
		if(saveOnNextFrame) {
			saveOnNextFrame = false;
			if(surface.saveToFile(saveDestination))
				message = "Successfully wrote to saves/" + saveDestination + ".yml";
			else
				message = "Write failed!";
			messageTimer = 180;
		}
		
		if(message.toLowerCase().contains("success"))
			displayMessage(new Color(0, 255, 0));
		else
			displayMessage(Color.RED);
		
		if(isInputtingText) {
			surface.fill(25);
			surface.stroke(0, 255, 0);
			surface.strokeWeight(3);
			surface.rect(20, 300 - 25, 800 - 40, 50, 10);
			surface.fill(255);
			surface.textAlign(DrawingSurface.CENTER);
			surface.textSize(25);
			String cursor = " ";
			if(ticks%30 < 15)
				cursor = "l";
			String text = ("Save to " + saveDestination + cursor + ".yml");
			surface.text(text, 400, 300 + 11);
		}
		
		surface.popStyle();
	}
	
	//Methods

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(resumeButton)) {
			surface.switchScreen(surface.GAME_SCREEN);
			messageTimer = 0;
		} else if(button.equals(exitButton)) {
			surface.switchScreen(surface.MENU_SCREEN);
			messageTimer = 0;
		} else if(button.equals(saveButton)) {
			isInputtingText = true;
		}
	}
	
	public void keyPressed() {
		if(isInputtingText) {
			boolean isShiftPressed = surface.isPressed(KeyEvent.VK_SHIFT);
			
			if(surface.keyCode == KeyEvent.VK_BACK_SPACE) {
				if(saveDestination.length() > 0)
					saveDestination = saveDestination.substring(0, saveDestination.length() - 1);
			} else if(surface.keyCode == KeyEvent.VK_PERIOD)
				saveDestination += ".";
			else if(surface.keyCode == KeyEvent.VK_MINUS && isShiftPressed)
				saveDestination += "_";
			else if(surface.keyCode == KeyEvent.VK_ESCAPE) {
				isInputtingText = false;
				saveDestination = "untitled";
			} else if(surface.keyCode == KeyEvent.VK_ENTER) {
				isInputtingText = false;
				if(saveDestination.endsWith(".yml")) {
					saveDestination = saveDestination.substring(0, saveDestination.length() - 4);
				}
				//saveButton.setText("Save to " + saveDestination.substring(0));
				
				message = "Writing to saves/" + saveDestination + ".yml...";
				messageTimer = 180;
				displayMessage(Color.RED);
				saveOnNextFrame = true;
			} else if(KeyEvent.getKeyText(surface.keyCode).length() == 1) {
				if(isShiftPressed)
					saveDestination += KeyEvent.getKeyText(surface.keyCode);
				else
					saveDestination += KeyEvent.getKeyText(surface.keyCode).toLowerCase();
			}
		}
	}
	
	public void displayMessage(Color color) {
		
		if(messageTimer > 0) {
			surface.pushStyle();
			surface.stroke(color.getRed(), color.getGreen(), color.getBlue());
			//stroke(0, 255, 0);
			surface.strokeWeight(3);
			surface.fill(25);
			surface.rect(5, 600 - 35, 800 - 10, 30, 10);
			surface.fill(255);
			surface.textSize(15);
			surface.textAlign(DrawingSurface.CENTER);
			surface.text(message, 400, 600 - 15);
			messageTimer--;
			surface.popStyle();
		}
	}
}
