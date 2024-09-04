package frog.screen;

import frog.DrawingSurface;
import frog.util.Button;
import processing.core.PImage;

public class VictoryScreen extends Screen {
	//Fields
	private Button menuButton;
	private PImage title;

	public VictoryScreen(DrawingSurface surface) {
		super(surface);
		
		title = surface.loadImage("resources/victory.png");
		
		menuButton = new Button(300, 375, 200, 50);
		menuButton.setText("Exit");
		menuButton.setButtonListener(this);
		buttons.add(menuButton);
		

	}
	
	public void draw() {

		surface.background(28, 29, 30);
		surface.pushStyle();
		surface.textAlign(DrawingSurface.CENTER, DrawingSurface.CENTER);
		surface.image(title, 100, 10);

		//surface.text("FROG DUNGEON", 350, 50);
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
		/*
		surface.pushMatrix();
		surface.translate(400, 200);
		surface.scale(0.8f, 0.8f);
		surface.image(gameOverImage, 0 - gameOverImage.width/2, 0 - gameOverImage.height/2);
		surface.popMatrix();
		*/

		surface.popStyle();
	}

	@Override
	public void buttonPressed(Button button) {
		if(button.equals(menuButton))
			surface.switchScreen(DrawingSurface.MENU_SCREEN);
	}
}
