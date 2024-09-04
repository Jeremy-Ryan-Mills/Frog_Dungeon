package frog.util;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;


/**
 * Represents a Button on the screen. To use, implement ButtonListener and add it to the Button.
 * @author Justin Hwang
 */
public class Button {
	/**
	 * ButtonListener that this Button calls when it is pressed
	 */
	private ButtonListener listener;
	
	/**
	 * Location and dimensions of the Button. X and Y are of top left corner.
	 */
	private int x, y, width, height;
	
	/**
	 * Radius of corners of Button
	 */
	private int radius;
	
	/**
	 * Text to display on the Button
	 */
	private String text;
	
	/**
	 * mainColor is the default color of Button,
	 * hoveredColor is its color when the mouse is hovering over it,
	 * and pressedColor is its color while being held down.
	 */
	private Color mainColor, hoveredColor, pressedColor;
	
	/**
	 * mainBorderColor is the default color of Button's border AND TEXT,
	 * hoveredBorderColor is border AND TEXT color when the mouse is hovering over the Button,
	 * and pressedBorderColor is border AND TEXT color while Button is being held down.
	 */
	private Color mainBorderColor, hoveredBorderColor, pressedBorderColor;
	
	/**
	 * Size of text, thickness of border of Rectangle
	 */
	private int textSize, strokeWeight;
	
	/**
	 * Current state of the Button.
	 * 0 = Untouched (default state)
	 * 1 = Hovered (mouse is hovered over Button)
	 * 2 = Pressed (mouse is pressed down on Button)
	 */
	private int state;
	
	public static final int DEFAULT = 0;
	public static final int HOVERED = 1;
	public static final int PRESSED = 2;
	
	
	
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//                   CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Constructor for new Button inside of a ButtonListener with given dimensions.
	 * 
	 * Default radius = 12
	 * Default mainColor & hoveredColor = Green
	 * Default pressedColor = White;
	 * Default border & text color (main, hovered, and pressed) = Black
	 * Default border weight = 5
	 * Default text size = 15
	 * Default text is "Button"
	 * @param x X coord of top left corner
	 * @param y Y coord of top left corner
	 * @param width Width of Button
	 * @param height Height of Button
	 */
	public Button(int x, int y, int width, int height) {
		this(x, y, width, height, 12);
	}
	
	public Button(Map<String, Object> map) {
		this.state = 0;
		this.x = (int) map.get("x");
		this.y = (int) map.get("y");
		this.width = (int) map.get("width");
		this.height = (int) map.get("height");
		this.radius = (int) map.get("radius");
		/*
		this.mainColor = new Color(0, 255, 0); // neon green
		this.hoveredColor = new Color(0, 180, 0); // darker green
		this.pressedColor = new Color(255, 255, 255); // white
		*/
		
		this.mainColor = colorFromMap((Map<String, Object>) map.get("mainColor"));
		this.hoveredColor = colorFromMap((Map<String, Object>) map.get("hoveredColor"));
		this.pressedColor =  colorFromMap((Map<String, Object>) map.get("pressedColor"));
		
		this.mainBorderColor = colorFromMap((Map<String, Object>) map.get("mainBorderColor"));
		this.hoveredBorderColor = colorFromMap((Map<String, Object>) map.get("hoveredBorderColor"));
		this.pressedBorderColor = colorFromMap((Map<String, Object>) map.get("pressedBorderColor"));
		this.strokeWeight = (int) map.get("strokeWeight");
		this.textSize = (int) map.get("textSize");
		this.text = (String) map.get("text");
	}
	
	/**
	 * Constructor for new Button with given dimensions AND radius
	 * 
	 * Default mainColor & hoveredColor = Green
	 * Default pressedColor = White;
	 * Default border & text color (main, hovered, and pressed) = Black
	 * Default border weight = 5
	 * Default text size = 15
	 * Default text is "Button"
	 * @param x X coord of top left corner
	 * @param y Y coord of top left corner
	 * @param width Width of Button
	 * @param height Height of Button
	 * @param radius Radius of Button's corners
	 */
	public Button(int x, int y, int width, int height, int radius) {
		this.state = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.radius = radius;
		/*
		this.mainColor = new Color(0, 255, 0); // neon green
		this.hoveredColor = new Color(0, 180, 0); // darker green
		this.pressedColor = new Color(255, 255, 255); // white
		*/
		
		this.mainColor = new Color(139, 182, 47); // green
		this.hoveredColor = new Color(72, 124, 56); // darker green
		this.pressedColor = new Color(247, 229, 215); // beige	
		
		this.mainBorderColor = new Color(28, 29, 30); // black --> dark gray
		this.hoveredBorderColor = new Color(28, 29, 30); // black
		this.pressedBorderColor = new Color(28, 29, 30); // black
		this.strokeWeight = 5;
		this.textSize = 15;
		this.text = "Button";
	}
	
	
	
	
	
	/**
	 * Draws the Button
	 * @post PApplet ends with the same style that it starts with
	 */
	public void draw(PApplet marker) {
		marker.pushStyle();
		
		//Setting correct color for state
		Color fillColor, borderColor;
		if(state == DEFAULT) {
			fillColor = mainColor;
			borderColor = mainBorderColor;
		} else if(state == HOVERED) {
			fillColor = hoveredColor;
			borderColor = hoveredBorderColor;
		} else if (state == PRESSED) {
			fillColor = pressedColor;
			borderColor = pressedBorderColor;
		} else { // SHOULD NEVER HAPPEN
			fillColor = new Color(0);
			borderColor = new Color(0);
		}
		
		//Draws primary Rectangle
		marker.fill(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
		marker.stroke(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
		marker.strokeWeight(5);
		marker.rect(this.x, this.y, this.width, this.height, this.radius);

		//Draws text
		marker.fill(borderColor.getRed(), borderColor.getGreen(), borderColor.getBlue());
		marker.textAlign(PApplet.CENTER);
		marker.text(text, this.x + this.width/2, y + this.height/2 + textSize/2);
		
		marker.popStyle();
	}
	
	/**
	 * Updates the Button with the new mouse info
	 * @param mouseX x-coord of the mouse, using actual coords, not assumed
	 * @param mouseY y-coord of the mouse, using actual coords, not assumed
	 * @param mousePressed True if mouse is currently pressed, false if not
	 */
	public void update(int mouseX, int mouseY, boolean mousePressed) {
		int oldState = state;
		
		if(isPointInside(mouseX, mouseY) && mousePressed)
			state = PRESSED;
		else if(isPointInside(mouseX, mouseY) && !mousePressed)
			state = HOVERED;
		else
			state = DEFAULT;
		
		if(oldState == PRESSED && state == HOVERED) // was pressed, mouse released inside of the Button
			if(listener != null)
				listener.buttonPressed(this);
		
	}
	
	/**
	 * Returns true if given coordinates are inside Button, false if not
	 * @param x x-coord of point to check
	 * @param y y-coord of point to check
	 * @return True if given coordinates are inside Button, false if not
	 */
	public boolean isPointInside(int x, int y) {
		return (x >= this.x && x < this.x + this.width && y > this.y && y < this.y + this.height);
	}
	
	/**
	 * See: public boolean isPointInside(int x, int y)
	 */
	public boolean isPointInside(Point point) {
		return this.isPointInside(point.x, point.y);
	}
	
	
	
	
	
	
	// GET AND SET METHODS
	
	/**
	 * Gets this Button's ButtonListener
	 */
	public ButtonListener getButtonListener() {
		return this.listener;
	}
	
	/**
	 * Sets this Button's ButtonListener that is called when this Button needs to be updated.
	 * @param listener ButtonListener to assign to this Button
	 */
	public void setButtonListener(ButtonListener listener) {
		this.listener = listener;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int getTextSize() {
		return textSize;
	}
	
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}
	
	public int getStrokeWeight() {
		return strokeWeight;
	}
	
	public void setStrokeWeight(int strokeWeight) {
		this.strokeWeight = strokeWeight;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Color getMainColor() {
		return mainColor;
	}
	
	public void setMainColor(Color c) {
		this.mainColor = c;
	}
	
	public Color getHoveredColor() {
		return hoveredColor;
	}
	
	public void setHoveredColor(Color c) {
		this.hoveredColor = c;
	}
	
	public Color getPressedColor() {
		return pressedColor;
	}
	
	public void setPressedColor(Color c) {
		this.pressedColor = c;
	}
	
	public Color getMainBorderColor() {
		return mainBorderColor;
	}
	
	public void setMainBorderColor(Color c) {
		this.mainBorderColor = c;
	}
	
	public Color getHoveredBorderColor() {
		return hoveredBorderColor;
	}
	
	public void setHoveredBorderColor(Color c) {
		this.hoveredBorderColor = c;
	}
	
	public Color getPressedBorderColor() {
		return pressedBorderColor;
	}
	
	public void setPressedBorderColor(Color c) {
		this.pressedBorderColor = c;
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("x", x);
		data.put("y", y);
		data.put("width", width);
		data.put("height", height);
		data.put("radius", radius);
		data.put("text", text);
		data.put("mainColor", mapFromColor(mainColor));
		data.put("hoveredColor",  mapFromColor(hoveredColor));
		data.put("pressedColor",  mapFromColor(pressedColor));
		data.put("mainBorderColor",  mapFromColor(mainBorderColor));
		data.put("hoveredBorderColor",  mapFromColor(hoveredBorderColor));
		data.put("pressedBorderColor",  mapFromColor(pressedBorderColor));
		data.put("textSize", textSize);
		data.put("strokeWeight", strokeWeight);
		
		return data;
	}
	
	private Map<String, Object> mapFromColor(Color c) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("r", c.getRed());
		data.put("g", c.getGreen());
		data.put("b", c.getBlue());
		return data;
	}
	
	private Color colorFromMap( Map<String, Object> map) {
		return new Color((int) map.get("r"), (int) map.get("g"), (int) map.get("b"));
	}
}
