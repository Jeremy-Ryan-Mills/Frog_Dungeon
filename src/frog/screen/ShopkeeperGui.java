package frog.screen;

import frog.DrawingSurface;
import frog.misc.HealthPotion;
import frog.misc.SpeedPotion;
import frog.misc.StrengthPotion;
import frog.util.Button;
import frog.weapons.Hammer;
import frog.weapons.Pistol;
import frog.weapons.Rifle;
import frog.weapons.Sword;
import processing.core.PImage;

/**
 * The GUI that shows up when you interact with the shopkeeper. Allows you to buy new items like different weapons or buffs.
 * @author Jeremy Mills
 *
 */
public class ShopkeeperGui extends Screen {
	
	private Button swordbutton;
	private Button hammerbutton;
	private Button pistolbutton;
	private Button riflebutton;
	private Button exit;
	private Button healthbutton;
	private Button speedbutton;
	private Button strengthbutton;
	private PImage shopkeeper;
	private PImage sword, hammer, pistol, rifle, health, speed, strength;


	public ShopkeeperGui(DrawingSurface surface) {
		super(surface);
		
		shopkeeper = surface.loadImage("resources/shopkeeper.png");
		sword = surface.loadImage("resources/sword.png");
		hammer = surface.loadImage("resources/hammer.png");
		pistol = surface.loadImage("resources/pistol.png");
		rifle = surface.loadImage("resources/rifle.png");
		health = surface.loadImage("resources/healthpotion.png");
		speed = surface.loadImage("resources/speedpotion.png");
		strength = surface.loadImage("resources/strengthpotion.png");

		
		//SWORD BUTTON
		swordbutton = new Button(25, 225, 150, 50);
		swordbutton.setText("Sword: $50");
		swordbutton.setButtonListener(this);
		buttons.add(swordbutton);
		
		hammerbutton = new Button(225, 225, 150, 50);
		hammerbutton.setText("Hammer: $75");
		hammerbutton.setButtonListener(this);
		buttons.add(hammerbutton);
		
		pistolbutton = new Button(425, 225, 150, 50);
		pistolbutton.setText("Pistol: $75");
		pistolbutton.setButtonListener(this);
		buttons.add(pistolbutton);
		
		riflebutton = new Button(625, 225, 150, 50);
		riflebutton.setText("Rifle: $100");
		riflebutton.setButtonListener(this);
		buttons.add(riflebutton);
		
		healthbutton = new Button(25, 400, 150, 50);
		healthbutton.setText("Health: $25");
		healthbutton.setButtonListener(this);
		buttons.add(healthbutton);
		
		speedbutton = new Button(225, 400, 150, 50);
		speedbutton.setText("Speed: $25");
		speedbutton.setButtonListener(this);
		buttons.add(speedbutton);
		
		strengthbutton = new Button(425, 400, 150, 50);
		strengthbutton.setText("Strength: $50");
		strengthbutton.setButtonListener(this);
		buttons.add(strengthbutton);
		
		exit = new Button(625, 400, 150, 50);
		exit.setText("Exit");
		exit.setButtonListener(this);
		buttons.add(exit);
		
	}

	/**
	 * Determines what actions to take if a button is pressed.
	 * @param button, the button that is clicked.
	 */
	public void buttonPressed(Button button) {
		if(button.equals(exit)) {
			surface.switchScreen(surface.GAME_SCREEN);
		} else if(button.equals(swordbutton)) {
			//PURCHASE SWORD
			if(surface.getFrog().getCoins() >= 50) {
				surface.getFrog().setMelee(new Sword(surface));
				surface.getFrog().incrementCoins(-50);
				surface.setMessage("Purchased Sword for $50!");
			}
		} else if(button.equals(hammerbutton)) {
			//PURCHASE HAMMER
			if(surface.getFrog().getCoins() >= 75) {
				surface.getFrog().setMelee(new Hammer(surface));
				surface.getFrog().incrementCoins(-75);
				surface.setMessage("Purchased Hammer for $75!");
			}
		} else if(button.equals(pistolbutton)) {
			//PURCHASE PISTOL
			if(surface.getFrog().getCoins() >= 75) {
				surface.getFrog().setProjectile(new Pistol(surface));
				surface.getFrog().incrementCoins(-75);
				surface.setMessage("Purchased Pistol for $75!");
			}
		} else if(button.equals(riflebutton)) {
			//PURCHASE RIFLE
			if(surface.getFrog().getCoins() >= 100) {
				surface.getFrog().setProjectile(new Rifle(surface));
				surface.getFrog().incrementCoins(-100);
				surface.setMessage("Purchased Rifle for $100!");
			}
		} else if(button.equals(healthbutton)) {
			//PURCHASE HEALTHPOTION
			if(surface.getFrog().getCoins() >= 25 && surface.getFrog().getHealth() < 100) {
				surface.getFrog().setHealth(surface.getFrog().getHealth()+HealthPotion.POTION_HEALTH);
				if(surface.getFrog().getHealth() > 100) {
					surface.getFrog().setHealth(100);
				}
				surface.getFrog().incrementCoins(-25);
				surface.setMessage("+" + (int)(HealthPotion.POTION_HEALTH) + " Health!");
			}
		} else if(button.equals(speedbutton)) {
			//PURCHASE SPEEDPOTION
			if(surface.getFrog().getCoins() >= 25 && surface.getFrog().getSpeed() < 1.5) {
				surface.getFrog().increaseSpeed(SpeedPotion.SPEED_BUFF);
				if(surface.getFrog().getSpeed() > 1.5) {
					surface.getFrog().increaseSpeed(1.5-surface.getFrog().getSpeed());
				}
				surface.getFrog().incrementCoins(-25);
				surface.setMessage("+" + (int)(SpeedPotion.SPEED_BUFF*100) + "% Speed!");
			}
		} else if(button.equals(strengthbutton)) {
			//PURCHASE STRENGTHPOTION
			if(surface.getFrog().getCoins() >= 50 && surface.getFrog().getStrength() < 2) {
				surface.getFrog().increaseStrength(StrengthPotion.STRENGTH_BUFF);
				if(surface.getFrog().getStrength() > 2) {
					surface.getFrog().increaseStrength(2-surface.getFrog().getStrength());
				}
				surface.getFrog().incrementCoins(-50);
				surface.setMessage("+" + (int)(StrengthPotion.STRENGTH_BUFF*100) + "% Strength!");
			}
		}
		
	}

	/**
	 * Draws the GUI
	 */
	public void draw() {
		surface.background(28, 29, 30);
		surface.pushStyle();
		surface.textAlign(DrawingSurface.CENTER, DrawingSurface.CENTER);
		surface.textSize(15);
		surface.text("Welcome to the shop!", 400, 40);
		surface.text("Feel free to browse.", 400, 60);
		surface.text("Coins: " + surface.getFrog().getCoins(), 400, 80);

		surface.image(shopkeeper, 350, 100, 100, 100);
		surface.image(sword, 70, 300, 75, 75);
		surface.image(hammer, 270, 300, 75, 75);
		surface.image(pistol, 470, 300, 75, 75);
		surface.image(rifle, 670, 300, 75, 75);
		surface.image(health, 70, 475, 75, 75);
		surface.image(speed, 270, 475, 75, 75);
		surface.image(strength, 470, 475, 75, 75);

			
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		surface.popStyle();
	}

}
