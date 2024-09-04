package frog.screen;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import frog.util.Button;
import frog.weapons.Projectile;
import processing.core.PApplet;
import processing.core.PImage;
import frog.DrawingSurface;
import frog.entities.Fly;
import frog.entities.Frog;
import frog.entities.Monster;
import frog.entities.Shopkeeper;
import frog.entities.Snake;
import frog.misc.*;

/**
 * The primary game screen that contains the maze, monsters, player, etc.
 * @author Justin Hwang, Mikaela Kwan, Jeremy Mills
 *
 */
public class FrogDungeon extends Screen {
	
	//Fields
	private Frog player;
	private Shopkeeper shopKeep;
	private BossTile boss;
	private ArrayList<Monster> monsters;
	private Button pauseButton;
	private boolean gamePaused;
	private ArrayList<Wall> walls;
	private ArrayList<Item> items;
	private int ticks;
	private ArrayList<String> currentMessages;
	private ArrayList<Integer> currentMessageTimes;
	private PImage frame1;
	private PImage frame2;
	private PImage floor;
	private String largeMessage;
	public static final int MAZE_SIZE = 10; //Maze will be MAZE_SIZE by MAZE_SIZE tiles
	
	//private ArrayList<Interactable> interactables //chests, shopkeepers, signs, etc
	private long lastTimeInMillis = 0;
	
	//Constructors
	/**
	 * Constructs a new FrogDungeon screen
	 * @param surface The DrawingSurface that the FrogDungeon Screen exists inside of
	 */
	public FrogDungeon (DrawingSurface surface) {
		super(surface);
		
		generateMaze(); //adds all the walls
		largeMessage = "";
		
		player = new Frog(300, 300, 40, 40, 100, surface);
		player.loadImages(surface);
		
		//shopKeep = new Shopkeeper(50, 50, 75, 75, 100, surface);
		Point shopTile = new Point((int) (Math.random() * 5), (int) (Math.random() * 5));
		int corner = (int) (Math.random() * 4);
		Point coordsInTile = new Point(200, 200);
		if(corner == 0)
			coordsInTile = new Point(10, 10);
		else if(corner == 1)
			coordsInTile = new Point(315, 10);
		else if(corner == 2)
			coordsInTile = new Point(315, 315);
		else if(corner == 3)
			coordsInTile = new Point(10, 315);
		//shopKeep = new Shopkeeper(Math.random()*3950, Math.random()*3950, 75d, 75d, 100d, surface);
		shopKeep = new Shopkeeper(shopTile.x * 400 + coordsInTile.x, shopTile.y * 400 + coordsInTile.y, 75.0, 75.0, 100.0, surface);
		
		//boss = new BossTile(0, 0);
		
		Point bossTile = new Point(5 + (int) (Math.random() * 5), 5 + (int) (Math.random() * 5));
		corner = (int) (Math.random() * 4);
		coordsInTile = new Point(200, 200);
		if(corner == 0)
			coordsInTile = new Point(10, 10);
		else if(corner == 1)
			coordsInTile = new Point(340, 10);
		else if(corner == 2)
			coordsInTile = new Point(340, 340);
		else if(corner == 3)
			coordsInTile = new Point(10, 340);
		//boss = new BossTile(2000+Math.random()*1950, 2000+Math.random()*1950, surface);
		boss = new BossTile(bossTile.x * 400 + coordsInTile.x, bossTile.y * 400 + coordsInTile.y, surface);
		//boss = new BossTile(400,400, surface);
		items = new ArrayList<Item>();
		//items.add(new HealthPotion(100, 100, 50, 50));
		//items.add(new SpeedPotion(350, 500, 50, 50));
		//items.add(new StrengthPotion(500, 350, 50, 50));
		
		monsters = new ArrayList<Monster>();
		monsters.add(new Fly(100, 300, 50, 50, 50, surface));
		//monsters.add(new Snake(100, 200, 50, 50, 50, surface));

		
		pauseButton = new Button(620, 20, 150, 100);
		pauseButton.setText("Pause Game");
		pauseButton.setButtonListener(this);
		buttons.add(pauseButton);
		
		currentMessages = new ArrayList<String>();
		currentMessageTimes = new ArrayList<Integer>();
		
		//brick = surface.loadImage("resources/brick.png");
		frame1 = surface.loadImage("resources/frame1.png");
		frame2 = surface.loadImage("resources/frame2.png");
		floor = surface.loadImage("resources/floor.png"); //MAKE SURE THAT FLOOR TILE IS 400x400, I DO NOT RESCALE IT
		
		
		for(int i = 0; i < MAZE_SIZE; i++) {
			for(int j = 0; j < MAZE_SIZE; j++) {
				
				boolean isInCorner = (i>=0 && i<=2 && j>=0 && j<=2);
				
				if(!isInCorner) {
					boolean willHaveMonsters = ((int)(Math.random()*2) == 0);
					
					if(willHaveMonsters) {
						int numMonsters = (int)(Math.random()*5+3);
						boolean isFly = ((((int)(Math.random()*10) >= 3)));
						for(int k = 0; k < numMonsters; k++) {
							
							int topLeftX = i*400;
							int topLeftY = j*400;
							
							int randomX = (int)(Math.random()*200 + 75);
							int randomY = (int)(Math.random()*200 + 75);
							boolean foundValidSpot = false;
							Monster m = null;
							while(foundValidSpot == false) {
								
								randomX = (int)(Math.random()*250 + 75);
								randomY = (int)(Math.random()*250 + 75);
								
								if(isFly) 
									m = new Fly(topLeftX + randomX, topLeftY + randomY, 50, 50, 50, surface);
								else
									m = new Snake(topLeftX + randomX, topLeftY + randomY, 50, 50, 75, surface);
								
								Rectangle rect = new Rectangle((int) m.getX(), (int) m.getY(), 50, 50);
								boolean touchedOneMonster = false;
								for(Monster otherM : monsters) {
									if(otherM.isTouching(rect))
										touchedOneMonster = true;
								}
								for(Wall w : walls) {
									for(Rectangle r : w.getRectangles())
										if(m.isTouching(r))
											touchedOneMonster = true;
								}
								
								if(touchedOneMonster == false)
									foundValidSpot = true;
							}
							
							monsters.add(m);
							
						}
					}
				}
			}
		}
		
		//surface.image(brick, 0, 0, 4000, 4000);
		//surface.image(brick, 0, 0, 50, 50);
		
		ticks = 0;
		//TODO: Create pauseButton and add to "buttons" arraylist inherited from Screen superclass
	}
	
	/**
	 * Creates new FrogDungeon from a file
	 * @param surface DrawingSurface used to create this FrogDungeon
	 * @param saveFile File to read from
	 */
	public FrogDungeon (DrawingSurface surface, File saveFile) {
		super(surface);
		largeMessage = "";
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(saveFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> data = yaml.load(inputStream);
		
		walls = new ArrayList<Wall>();
		ArrayList<Map<String, Object>> wallMaps = (ArrayList<Map<String, Object>>) data.get("walls");
		//System.out.println(wallMaps);
		for(Map<String, Object> map : wallMaps) {
			walls.add(new Wall(map));
		}
		
		Map<String, Object> playerMap = (Map<String, Object>) data.get("player");
		player = new Frog(playerMap, surface);
		player.loadImages(surface);
		

		Map<String, Object> shopKeepMap = (Map<String, Object>) data.get("shopKeep");
		shopKeep = new Shopkeeper(shopKeepMap, surface);
		//shopKeep = new Shopkeeper(Math.random()*3950, Math.random()*3950, 50d, 50d, 100d);
		//boss = new BossTile(0, 0);
		
		//boss = new BossTile(2000+Math.random()*1950, 2000+Math.random()*1950);

		Map<String, Object> bossTileMap = (Map<String, Object>) data.get("boss");
		boss = new BossTile(bossTileMap, surface);
		
		items = new ArrayList<Item>();
		ArrayList<Map<String, Object>> itemMaps = (ArrayList<Map<String, Object>>) data.get("items");
		//System.out.println(itemMaps);
		for(Map<String, Object> map : itemMaps) {
			if(map.get("type").equals("HealthPotion"))
				items.add(new HealthPotion(map));
			else if(map.get("type").equals("SpeedPotion"))
				items.add(new SpeedPotion(map));
			else if(map.get("type").equals("StrengthPotion"))
				items.add(new StrengthPotion(map));
		}
		
		monsters = new ArrayList<Monster>();
		ArrayList<Map<String, Object>> monsterMaps = (ArrayList<Map<String, Object>>) data.get("monsters");
		//System.out.println(monsterMaps);
		for(Map<String, Object> map : monsterMaps) {
			if(map.get("type").equals("Fly"))
				monsters.add(new Fly(map, surface));
			else if(map.get("type").equals("Snake"))
				monsters.add(new Snake(map, surface));
		}
		
		
		//monsters.add(new Fly(100, 300, 50, 50, 50, surface));
		//monsters.add(new Snake(100, 200, 100, 50, 50, surface));

		Map<String, Object> pauseButtonMap = (Map<String, Object>) data.get("pauseButton");
		pauseButton = new Button(pauseButtonMap);
		pauseButton.setButtonListener(this);
		buttons.add(pauseButton);
		
		//currentMessages = new ArrayList<String>();
		//currentMessageTimes = new ArrayList<Integer>();
		currentMessages = (ArrayList<String>) data.get("currentMessages");
		currentMessageTimes = (ArrayList<Integer>) data.get("currentMessageTimes");
		
		//brick = surface.loadImage("resources/brick.png");
		frame1 = surface.loadImage("resources/frame1.png");
		frame2 = surface.loadImage("resources/frame2.png");
		floor = surface.loadImage("resources/floor.png"); //MAKE SURE THAT FLOOR TILE IS 400x400, I DO NOT RESCALE IT
		
		//surface.image(brick, 0, 0, 4000, 4000);
		//surface.image(brick, 0, 0, 50, 50);
		
		ticks = (int) data.get("ticks");
		//TODO: Create pauseButton and add to "buttons" arraylist inherited from Screen superclass
	}
	
	//Methods
	
	/**
	 * Draws everything
	 */
	public void draw() {
		
		boolean hasLargeMessage = false;

		player.move(walls, surface);
		//surface.image(brick, 0, 0);
		
		if (boss.isInsideTile(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2)) {
		//if (boss.isInsideTile(player.getX(), player.getY())) {
			boss.changeScreen(surface);
			player.moveTo(player.getX() + 50, player.getY() + 50);
		}	
		
		surface.background(28, 29, 30);
		//drawFloor();

		surface.pushMatrix();
		//surface.background(0);
		surface.translate(0 - (float) player.getX() - (float) player.getWidth()/2 + 400, 0 - (float) player.getY() - (float) player.getHeight()/2 + 300); 

		//drawFloor();
		
		
		//for(int i = 0; i < surface.width; i+=1200) {
			//for(int j = 0; j < surface.height; j+=1200) {
				//if(i > player.getX()-400-1200 && i < player.getX() + player.getWidth() && j > player.getY() - 400 - 1200 && j < player.getY() + player.getHeight()) {
					//surface.image(brick, i, j, 1200, 1200);
				//}
			//}
		//}
		
		shopKeep.draw(surface);
		boss.draw(surface);
		player.draw(surface);
		
		//WALLS DRAWING
		drawWalls();
		
		//ITEMS DRAWING

		int screenLeft = (int) (player.getX() - 400 + player.getWidth()/2);
		int screenTop = (int) (player.getY() - 300 + player.getHeight()/2);
		int screenRight = (int) (player.getX() + 400 + player.getWidth());
		int screenBottom = (int) (player.getY() + 300 + player.getHeight());
		for(int i = 0; i < items.size(); i++) {
			Rectangle hb = new Rectangle((int)(items.get(i).getX()), (int)(items.get(i).getY()), (int)(items.get(i).getWidth()), (int)(items.get(i).getHeight()));
			if(player.isTouching(hb)) {
				largeMessage = "E to pick up item";
				hasLargeMessage = true;
			}
			if (surface.isPressed(KeyEvent.VK_E) && player.isTouching(hb)) {
				items.get(i).doAction(this);
				items.remove(i);
				i--;
			} else if(hb.x > screenLeft - hb.width && hb.x < screenRight && hb.y > screenTop - hb.height && hb.y < screenBottom){
					items.get(i).draw(surface);
			}
				
		}
		
		
		//MONSTER DRAWINGS
		for(int i = 0; i < monsters.size(); i++) {
			if(monsters.get(i).getHealth() > 0) {
				//ArrayList<Rectangle> hitBoxes = new ArrayList<Rectangle>();
				//for(int j = 0; j < monsters.size(); j++) {
					//hitBoxes.add(new Rectangle((int)(monsters.get(j).getX()), (int)(monsters.get(j).getY()), (int)(monsters.get(j).getWidth()), (int)(monsters.get(j).getHeight())));
				//}
				
				
				monsters.get(i).move(walls, monsters, player.getX(), player.getY());
				monsters.get(i).draw(surface);
				Rectangle hb = new Rectangle((int)(monsters.get(i).getX()), (int)(monsters.get(i).getY()), (int)(monsters.get(i).getWidth()), (int)(monsters.get(i).getHeight()));
				if(player.isTouching(hb)) {
					//PUT THE DAMAGE METHOD HERE
					if(ticks % 60 == 0) {
						player.setHealth(player.getHealth()-monsters.get(i).getDamage());
					}
					
					
				}
				
			}
			else {
				if(monsters.get(i).getItem() != null) {
					//System.out.println(monsters.get(i).getItem().getX() + " " + monsters.get(i).getItem().getY());
					items.add(monsters.get(i).getItem());
				}
				setMessage("+" + monsters.get(i).getCoinValue() + " Coins!");
				player.incrementCoins(monsters.remove(i).getCoinValue());
				
			}
		}
		
		//SHOPKEEPER
		//if(player.isTouching(new Rectangle((int)(shopKeep.getX() - shopKeep.getWidth()/2), (int)(shopKeep.getY() - shopKeep.getHeight()/2), (int)(shopKeep.getWidth()), (int)(shopKeep.getHeight())))) {
		if(player.isTouching(new Rectangle((int) shopKeep.getX(), (int) shopKeep.getY(), (int) shopKeep.getWidth(), (int) shopKeep.getHeight()))) {	
			largeMessage = "E to open Shop Keeper";
			
			hasLargeMessage = true;
		}
		//if (surface.isPressed(KeyEvent.VK_E) && player.isTouching(new Rectangle((int)(shopKeep.getX() - shopKeep.getWidth()/2), (int)(shopKeep.getY() - shopKeep.getHeight()/2), (int)(shopKeep.getWidth()), (int)(shopKeep.getHeight())))) {
		if(surface.isPressed(KeyEvent.VK_E) && player.isTouching(new Rectangle((int) shopKeep.getX(), (int) shopKeep.getY(), (int) shopKeep.getWidth(), (int) shopKeep.getHeight()))) {	
			shopKeep.changeScreen(surface);
		}
		
		ArrayList<Projectile> p = player.getProjectile().getProjectiles();
		if (p.size() > 0)
		for (int i = 0; i < p.size(); i++) {
			if(p.get(i).shouldDie() || p.get(i).isTouchingWall(walls) || p.get(i).hitMonster(monsters)) {
				p.remove(i);
				i--;
			} else {
				p.get(i).move();
				p.get(i).draw(surface);
			}
		}
		
		
		surface.popMatrix();
		
		surface.fill(255);
		surface.image(frame1, -5, -10, 200, 150);
		//surface.rect(20, 20, 150, 100);
		surface.fill(0);
		surface.text("Health: " + player.getHealth() + "\nSpeed: " +  ((int)(player.getSpeed() * 100))/100.0 + "\nStrength: " + ((int)(player.getStrength() * 100))/100.0 + "\nCoins: " + player.getCoins(), 50, 45);
		surface.fill(255);
		surface.rect(200, 30, 200, 35, 8);
		surface.fill(228, 74, 74);
		surface.rect(200, 30, (float)((player.getHealth()/100)*200), 35, 8);
		surface.fill(255);
		
		//System.out.println(currentMessages.size());
		surface.pushStyle();
		surface.textSize(25);
		for(int i = 0; i < currentMessages.size(); i++) {
				
			//if(ticks > currentMessageTimes.get(i) && ticks < currentMessageTimes.get(i) + 180) {
			
			surface.text(currentMessages.get(i), 25, 175 + (i*40));
				
			if(currentMessageTimes.get(i) + 180 < ticks) {
				currentMessageTimes.remove(i);
				currentMessages.remove(i);
				i--;
			}
			//}
			//else {
				//currentMessageTimes.remove(i);
				//currentMessages.remove(i);
			//}
		}
		
		surface.textAlign(surface.CENTER);
		if(hasLargeMessage)
			surface.text(largeMessage, 400, 575);
		surface.popStyle();
			
		
		surface.image(frame2, 425, -10, 180, 150);
		
		if(player.getMelee() != null) {
			player.getMelee().draw(surface, 450, 40, 50, 50);
		}
		if(player.getProjectile() != null) {
			player.getProjectile().draw(surface, 525, 40, 50, 50);
		}
		
		//draws mini-map
		surface.fill(100);
		surface.stroke(255);
		surface.rect(5, 485, 110, 110, 5);
		surface.fill(255);
		surface.noStroke();
		//surface.ellipse((float) (10 + 100*player.getX()/(Wall.WALL_WIDTH * MAZE_SIZE)), (float) (490 + 100*player.getY()/(Wall.WALL_WIDTH * MAZE_SIZE)), 5, 5);
		surface.image(shopKeep.getImage(), (float) (10 + 100*shopKeep.getX()/(Wall.WALL_WIDTH * MAZE_SIZE)) - 12, (float) (490 + 100 * shopKeep.getY()/(Wall.WALL_WIDTH * MAZE_SIZE)) - 12, 25, 25); 
		surface.image(player.getImage(), (float) (10 + 100*player.getX()/(Wall.WALL_WIDTH * MAZE_SIZE)) - 12, (float) (490 + 100 * player.getY()/(Wall.WALL_WIDTH * MAZE_SIZE)) - 12, 25, 25); 
		surface.image(boss.getImage(), (float) (100*boss.getX()/(Wall.WALL_WIDTH * MAZE_SIZE)), (float) (490 + 100 * boss.getY()/(Wall.WALL_WIDTH * MAZE_SIZE)) - 10, 20, 20); 
		
		//FPS
		surface.fill(255);
		if(System.currentTimeMillis() != 0)
			surface.text("FPS: " + 1000/(System.currentTimeMillis() - lastTimeInMillis) + "", 750, 580);
		lastTimeInMillis = System.currentTimeMillis();
		
		updateButtons(surface.assumedCoordinatesToActual(surface.mouseX, surface.mouseY), surface.mousePressed);
		drawButtons(surface);
		
	
		if(player.getHealth() <= 0)
			surface.switchScreen(DrawingSurface.GAME_OVER_SCREEN);
		ticks++;
	}
	
	/**
	 * Is called when a Button is pressed
	 */
	@Override
	public void buttonPressed(Button button) {
		if(button.equals(pauseButton)) {
			surface.switchScreen(surface.PAUSE_SCREEN);
		}
	}
	
	/**
	 * Generates a new maze.
	 * @post walls field will be populated with all walls
	 */
	public void generateMaze() {
		// adds all the walls to the maze
		walls = new ArrayList<Wall>();
		for (double x = 0; x <= MAZE_SIZE; x += 0.5) {
			if ((int) x == x) { // if x is a whole number
				for (double y = 0.5; y < MAZE_SIZE; y++)
					walls.add(new Wall(x, y));
			} else { // x is not a whole number, walls are horizontal
				for (double y = 0; y <= MAZE_SIZE; y++)
					walls.add(new Wall(x, y));
			}
		}
		
		ArrayList<Wall> wallsToProcess = new ArrayList<Wall>();
		ArrayList<Point> tilesAlreadyAdded = new ArrayList<Point>();
		
		tilesAlreadyAdded.add(new Point(0, 0)); //starts in upper left hand corner
		wallsToProcess.addAll(getNeighboringWalls(new Point(0, 0))); //adds four walls to start with
		
		while(wallsToProcess.size() > 0) {
			Wall wall = wallsToProcess.get((int) (Math.random() * wallsToProcess.size()));
			Point[] possibleTiles = wall.getBothNeighbors();
			
			Point newTile = null;
			
			if(!listContainsPoint(tilesAlreadyAdded, possibleTiles[0]) && isTileInMaze(possibleTiles[0])) {
				newTile = possibleTiles[0];
			} else if(!listContainsPoint(tilesAlreadyAdded, possibleTiles[1]) && isTileInMaze(possibleTiles[1])) {
				newTile = possibleTiles[1];
			}
			
			if(newTile != null) {
				tilesAlreadyAdded.add(newTile);
				
				//randomly generates if wall should be empty or a doorway. Doorway 1/3 of time, empty 2/3 of time
				int num = (int) (Math.random() * 3);
				if(num == 0)
					wall.setType(Wall.DOORWAY);
				else
					wall.setType(Wall.EMPTY);
				ArrayList<Wall> newWalls = getNeighboringWalls(newTile);
				for(Wall w : newWalls)
					if(!wallsToProcess.contains(w))
						wallsToProcess.add(w);
			}
			
			wallsToProcess.remove(wall);
		}
		
		//removes empty walls
		for(int i = 0; i < walls.size(); i++) {
			if(walls.get(i).getType() == Wall.EMPTY) {
				walls.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Returns true if point is within the maze, false if not
	 * @param point
	 * @return true if tile is in maze, false if tile is not within maze
	 */
	private boolean isTileInMaze(Point point) {
		return (point.x >= 0 && point.x < MAZE_SIZE && point.y >= 0 && point.y < MAZE_SIZE);
	}
	
	/**
	 * Returns true if an equivalent Point exists within the list, false if otherwise.
	 * (This must be used instead of list.contains(), because that searches for the same object, not for equivalent copies of the object)
	 */
	private boolean listContainsPoint(ArrayList<Point> list, Point point) {
		for(Point p : list)
			if(p.x == point.x && p.y == point.y)
				return true;
		return false;
	}
	
	/**
	 * Returns all Walls around the given tile
	 * @return ArrayList of Walls. Max length is 4, min length is 0 if no walls exist.
	 */
	private ArrayList<Wall> getNeighboringWalls(Point p) {
		ArrayList<Wall> neighbors = new ArrayList<Wall>();
		neighbors.add(getWallAt(p.x, p.y + 0.5));
		neighbors.add(getWallAt(p.x + 1, p.y + 0.5));
		neighbors.add(getWallAt(p.x + 0.5, p.y));
		neighbors.add(getWallAt(p.x + 0.5, p.y + 1));
		
		for(int i = neighbors.size() - 1; i >= 0; i--)
			if(neighbors.get(i) == null)
				neighbors.remove(i);
		
		return neighbors;
	}
	
	/**
	 * Returns the Wall at the given coordinates if it exists, null if it does not exist.
	 */
	private Wall getWallAt(double x, double y) {
		for(Wall wall : walls) {
			if(wall.getX() == x && wall.getY() == y)
				return wall;
		}
		return null;
	}
	
	public Frog getFrog() {
		return player;
	}
	
	public void setMessage(String msg) {
		currentMessages.add(msg);
		currentMessageTimes.add(ticks);
	}
	
	
	/**
	 * Draws monsters onto Maze.
	 */
	public void drawMonsters() {
		
	}
	
	/**
	 * Draws walls.
	 */
	public void drawWalls() {
		//draws the walls that are near the screen
		int screenLeft = (int) (player.getX() - 400 + player.getWidth()/2);
		int screenTop = (int) (player.getY() - 300 + player.getHeight()/2);
		int screenRight = (int) (player.getX() + 400 + player.getWidth());
		int screenBottom = (int) (player.getY() + 300 + player.getHeight());
		for(Wall wall : walls) {
			Point pixelCoords = Wall.wallCoordsToPixelCoords(wall.getX(), wall.getY());
			pixelCoords.setLocation(pixelCoords.getX() - wall.getWidth()/2, pixelCoords.getY() - wall.getHeight()/2);
			if(pixelCoords.x > screenLeft - wall.getWidth() && pixelCoords.x < screenRight && pixelCoords.y > screenTop - wall.getHeight() && pixelCoords.y < screenBottom)
				wall.draw(surface);
		}
	}
	
	public void mousePressed() {
		
		if (surface.mouseButton == surface.LEFT) {
			for(int i = 0; i < monsters.size(); i++) {
	
				Rectangle hb = new Rectangle((int)(monsters.get(i).getX()), (int)(monsters.get(i).getY()), (int)(monsters.get(i).getWidth()), (int)(monsters.get(i).getHeight()));
				if(player.isTouching(hb)) {
					player.meleeAttack(monsters.get(i));
						
				}		
			}
		} else if (surface.mouseButton == surface.RIGHT) {
				//System.out.println("Click X = " + surface.mouseX + ", Click Y = " + surface.mouseY);
				player.shootRangedWeapon(surface.mouseX, surface.mouseY, this);

		}
		
	}
	
	private void drawFloor() {
		long start = System.currentTimeMillis();
		int screenLeft = (int) (player.getX() - 400 + player.getWidth()/2);
		int screenTop = (int) (player.getY() - 300 + player.getHeight()/2);
		int screenRight = (int) (player.getX() + 400 + player.getWidth());
		int screenBottom = (int) (player.getY() + 300 + player.getHeight());
		
		
		
		//surface.image(floor, 0, 0);
		for(int x = 0; x < MAZE_SIZE * 400; x += 400) {
			for(int y = 0; y < MAZE_SIZE * 400; y += 400) {
				if(x > screenLeft - 400 && x < screenRight && y > screenTop - 400 && y < screenBottom) {
					//surface.scale(0.1f, 0.1f);
					//surface.image(floor, x - screenLeft, y - screenTop);
					

					//surface.image(floor, x, y);
				}
			}
		}
		surface.text("" + (System.currentTimeMillis() - start), (float) player.getX(), (float) player.getY() - 20);
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public Map<String, Object> asMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("player", player.asMap());
		data.put("shopKeep", shopKeep.asMap());
		data.put("boss", boss.asMap());
		
		ArrayList<Map<String, Object>> monsterMaps = new ArrayList<Map<String, Object>>();
		for(Monster m : monsters) {
			monsterMaps.add(m.asMap());
		}
		data.put("monsters", monsterMaps);
		
		data.put("pauseButton", pauseButton.asMap());
		data.put("gamePaused", gamePaused);
		
		ArrayList<Map<String, Object>> wallMaps = new ArrayList<Map<String, Object>>();
		for(Wall w : walls) {
			wallMaps.add(w.asMap());
		}
		data.put("walls", wallMaps);
		
		ArrayList<Map<String, Object>> itemMaps = new ArrayList<Map<String, Object>>();
		for(Item i : items) {
			itemMaps.add(i.asMap());
		}
		data.put("items", itemMaps);
		
		data.put("ticks", ticks);
		data.put("currentMessages", currentMessages);
		data.put("currentMessageTimes", currentMessageTimes);
		//data.put("frame1", frame1);
		//data.put("frame2", frame2);
		//data.put("floor", floor);
		data.put("lastTimeInMillis", lastTimeInMillis);
		//data.put("surface", surface);
		
		/*ArrayList<Map<String, Object>> buttonMaps = new ArrayList<Map<String, Object>>();
		for(Button b : buttons) {
			//buttonMaps.add(b.asMap());
		}
		data.put("buttons", buttonMaps);*/
		
		return data;
	}
}
