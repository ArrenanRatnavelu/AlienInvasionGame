package coe528.project;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.*;
import java.text.AttributedString;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.*;
//OVERVIEW: Mutable, game object that uses other classes in project to build game, complete with gamescreen and characters
public class AlienInvasion extends JPanel implements Runnable, KeyListener{
	private MainPanel main;
	public final static int MAXWIDTH = 1024, MAXHEIGHT = 768; //width & height of JPanel
	private boolean running; //keeps track of state of the program
	private Thread thread;
	private Graphics2D graphics;
	private Image image; //used for double buffering
        private BufferedImage originalImage;
	
	//game objects
	private LevelLoader levelLoad;
	private Enemy[][] enemyGrid;
	private Entity player;
	private Entity playerBullet;
        private Entity AIbullet;
        private Entity AIBulletHorizontal;
        
	
	//keyboard variables
	private boolean[] keysPressed = new boolean[6];
	private final int KEY_LEFT = 0, KEY_RIGHT = 1, KEY_SPACE = 2, KEY_ENTER = 3, KEY_UP = 4, KEY_DOWN = 5;
	
	//bullet states
	private int bulletState;
	private final static int BULLET_START = 0;
	private final static int BULLET_MOVING = 1;
	
	//game states
	private int gameState;
	private final static int GAME_START = 0;
	private final static int GAME_PLAY = 1;
	private final static int GAME_LOSE = 2;
        private final static int GAME_WIN = 3;
	
	//game components
	private int score;
	private int lives;
	private int level;
        
        //gun states
        private int AIState;
	private final static int AIstate_START = 0;
	private final static int AIstate_MOVING = 1;
        
        private int AIStateH;
	private final static int AIstateH_START = 0;
	private final static int AIstateH_MOVING = 1;
        
	
	public AlienInvasion(MainPanel main) {
		this.setDoubleBuffered(false); //we'll use our own double buffering method
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(MAXWIDTH, MAXHEIGHT));
		this.setFocusable(true);
		this.requestFocus();
		addKeyListener(this);
		this.main = main;
	}
	
	public static void main(String[] args) {
		new MainPanel();
	}
	
	public void addNotify() {
		super.addNotify();
		startGame();
	}
	
	public void stopGame() {
            //MODIFIES: running
            //EFFECTS:sets running to false 
		running = false;
	}
	
	//Creates a thread and starts it
	public void startGame() {
            //EFFECTS: calls run method 
		if (thread == null || !running) {
			thread = new Thread(this);
		}
		thread.start(); 
	}
	
	public void run() {
            //MODIFIES: running
            //EFFECTS: keeps game running by updating game and graphics
		running = true;

		
		init();
		
		
		while (running) {
			createImage(); //creates image for double buffering
			updateGame();
			drawImage(); //draws on the JPanel
		}
		
		levelLoad.closeFile();
		System.exit(0);
	}
	
	public void init() {
            //EFFECTS: calls other initialize methods to initialize game components
		initializeGame();
		initializeLevelGrid();
		initializePlayer();
		initializeBall();
                initializeAIBullet();

	}
	
	public void initializeGame() {
            //EFFECTS: initializes score, lives and level values
		score = 0;
		lives = 3;
		level = 1;
	}
	
	public void initializeLevelGrid() {
            //MODIFIES: gameState
            //EFFECTS: initalizes levelLoader object and calls levelLoader methods to create level. Changes game state to win if both levels are beat
		levelLoad = new LevelLoader();
		if(level == 1){
			levelLoad.openLevelFile("level1.txt");
                        levelLoad.createLevelGrid();
                        enemyGrid = levelLoad.getEnemyGrid();
                }
                else if(level == 2){
			levelLoad.openLevelFile("level2.txt");
                        levelLoad.createLevelGrid();
                        enemyGrid = levelLoad.getEnemyGrid();
                }
                else if(level > 2){
                        gameState = GAME_WIN;
                }
		
	}
	
	public void initializePlayer() {
            //initializes player object
		player = new Player(MAXWIDTH/2 - 75, 700, 100, 65, Color.white);

	}
	
	public void initializeBall() {
            //EFFECTS: initializes player bullet object
		bulletState = BULLET_START;
		playerBullet = new Bullet(MAXWIDTH/2 - 7, MAXHEIGHT/2, 14, 5, Color.red);
                
	}
	
        public void initializeAIBullet(){
            //EFFECTS: initializes bullet objects
                
                AIbullet = new Bullet(MAXWIDTH/2 - 7, MAXHEIGHT/2, 14, 14, Color.blue);
                AIBulletHorizontal = new Bullet( MAXWIDTH/2 - 7, MAXHEIGHT/2,  14, 14, Color.white);
        }
        
        
	public void updateGame() {
            //REQUIRES: drawBackground(), drawLevelGrid(), updateGameState(), updatePlayer(), drawPlayer()
            //updateBall(), updateGun(), checkforCollisions(), drawGun(), drawBall(), drawScore(), drawLives()
            //must satisfy their own specifications.
            //EFFECTS: Calls graphics and gameplay related methods to draw game to screen
            //and to allow game to use gameplay mechanics.
		
               
                drawBackground();
		drawStartScreen();		
		drawLevelGrid(); //do this
		
		updateGameState();
	
		updatePlayer();
		drawPlayer();
		
		updateBullet();
                updateAIBullet();
                
		checkForCollisions();
                
		drawAIBullet();
                drawBullet();
                
                
		
		drawScore();
                drawLives();
	}
        
        public void updateGameState() {
            //REQUIRES: gameState is initialized
            //MODIFIES: gameState variable
            //EFFECTS: Based on gameplay, sets gameState to different state
		if(lives <= 0) {
			gameState = GAME_LOSE;
		}
		
		if(gameState == GAME_START) {
			initializeGame();
			initializeLevelGrid();
			gameState = GAME_PLAY;
                        
		}
		else if(gameState == GAME_LOSE) {
			drawLoseScreen();
                        AIState = AIstate_START;
                        bulletState = BULLET_START;
                        AIStateH = AIstateH_START;
			if(keysPressed[KEY_SPACE]) {
				gameState = GAME_START;
				lives = 3;
				level = 1;
			}
		}
                else if(gameState == GAME_WIN){
                    drawWinScreen();
                    AIState = AIstate_START;
                        bulletState = BULLET_START;
			if(keysPressed[KEY_SPACE]) {
				gameState = GAME_START;
				lives = 3;
				level = 1;
                    }
                }
		if(winConditionReached()) {
			gameState = GAME_PLAY;
			level++;
			initializeLevelGrid();
			resetBullet();
		}
	}
	
	public void drawBackground() {
            //REQUIRES: File being loaded in must exist in directory
            //EFFECTS: Draws background image on screen in game window.
                try {

			originalImage = ImageIO.read(new File(
					"background.jpg"));
                      
		} catch (IOException e) {
			System.out.println("FAIL");
		}
                
                graphics.drawImage(originalImage, 0, 0, MAXWIDTH, MAXHEIGHT, this);
	}
	
	public void drawLevelGrid() {
            //REQUIRES: enemyGrid must exist
            //EFFECTS: calls drawEnemy for all indexes of enemyGrid
		for(int row=0; row < enemyGrid.length; row++) {
			for(int col=0; col < enemyGrid[0].length; col++) {
				if(enemyGrid[row][col] != null)
					drawEnemy(row, col);
			}
		}
	}
	
	public void drawEnemy(int row, int col) {
            //REQUIRES: row >= 0, col >= 0
            //EFFECTS: Draws the brick whose attributes are found in the brickGrid
		Enemy currentEnemy = enemyGrid[row][col];
		if(currentEnemy.isAlive()) {
			int x = currentEnemy.getX();
			int y = currentEnemy.getY();
			int w = currentEnemy.getWidth();
			int h = currentEnemy.getHeight();
                        if(currentEnemy.getColour() == Color.red){
                            try {

                                originalImage = ImageIO.read(new File(
                                            "alienred.png"));

                            } catch (IOException e) {
                                    System.out.println("Sprite not found! Check directory");
                              }
                        }
                        if(currentEnemy.getColour() == Color.blue){
                            try {

                                originalImage = ImageIO.read(new File(
                                            "alienblue.png"));

                            } catch (IOException e) {
                                    System.out.println("Sprite not found! Check directory");
                              }
                        }
                        if(currentEnemy.getColour() == Color.green){
                            try {

                                originalImage = ImageIO.read(new File(
                                            "aliengreen.png"));

                            } catch (IOException e) {
                                    System.out.println("Sprite not found! Check directory");
                              }
                        }
                graphics.drawImage(originalImage, x, y, w, h, this);
			
		}
	}
	
	public void updatePlayer() {
            //REQUIRES: Player object must exist
            //MODIFIES: player velocityX, velocityY, x and y
            //EFFECTS: based on gameplay sets new values of attributes
		if(keysPressed[KEY_LEFT])
			player.setVelocityX(-7);
		else if(keysPressed[KEY_RIGHT])
			player.setVelocityX(7);
		else
			player.setVelocityX(0);
                
                if(keysPressed[KEY_UP])
                        player.setVelocityY(-7);
                else if(keysPressed[KEY_DOWN])
                        player.setVelocityY(7);
                else
                        player.setVelocityY(0);
                
		int newXPosition = player.getX() + player.getVelocityX();
                
                int newYPosition = player.getY() + player.getVelocityY();
		
		if(newXPosition + player.getWidth() > MAXWIDTH)
			player.setX(MAXWIDTH - player.getWidth());
		else if(newXPosition < 0)
			player.setX(0);
		else
			player.setX(newXPosition);
                
                if(newYPosition + player.getHeight() > MAXHEIGHT - 30)
			player.setY(MAXHEIGHT - 30 - player.getHeight());
		else if(newYPosition < 200)
			player.setY(200);
		else
			player.setY(newYPosition);
                
                
                
                
	}
	
	public void drawPlayer() {
            //REQUIRES: player object exists
            //EFFECTS: loads player sprite image into specified player object position
		int x = player.getX();
		int y = player.getY();
		int w = player.getWidth();
		int h = player.getHeight();
                 
                try {

			originalImage = ImageIO.read(new File(
					"ship.png"));
                      
		} catch (IOException e) {
			System.out.println("FAIL");
		}
                
                graphics.drawImage(originalImage, x, y, w, h, this);
	}
        
	public void updateBullet() {
             //REQUIRES: Bullet object must exist
            
            //EFFECTS: based on bullet position, resets bullet
		if(bulletState == BULLET_START) {
			resetBullet();
		}
		else if(bulletState == BULLET_MOVING) {
			
			if(playerBullet.getY() + playerBullet.getVelocityY() >= MAXHEIGHT) {
				
				resetBullet();
			}
			else if(playerBullet.getY() + playerBullet.getVelocityY() < 50)
				
                                resetBullet();
			playerBullet.setY(playerBullet.getY() + playerBullet.getVelocityY());
		}
	}
        
        public void drawBullet() {
            //REQUIRES: bullet object exists
            //EFFECTS: draws ellipse object into specified object position
		int x = playerBullet.getX();
		int y = playerBullet.getY();
		int w = playerBullet.getWidth();
		int h = playerBullet.getHeight();
		Ellipse2D ballRec = new Ellipse2D.Double(x, y, w, h);
		graphics.setColor(playerBullet.getColour());
		graphics.fill(ballRec);
	}
        
	public void resetBullet() {
            
            //REQUIRES: bullet object must exist
            //MODIFIES: bullet velocityX, velocityY, x and y, bulletState
            //EFFECTS: based on gameplay sets new values of attributes
            
		playerBullet.setX(player.getX() + player.getWidth()/2);
		playerBullet.setY(player.getY() + player.getHeight()/2);
		playerBullet.setVelocityX(0);
		playerBullet.setVelocityY(-16);
		bulletState = BULLET_START;
	}
	
        public void updateAIBullet() {
		if(AIState == AIstate_START) {
			resetAIBullet();
		}
                else if(AIState == AIstate_MOVING) {
			
			if(AIbullet.getY() + AIbullet.getVelocityY() >= MAXHEIGHT) {
				
				resetAIBullet();
			}
			else if(AIbullet.getY() + AIbullet.getVelocityY() < -50)
				
                                resetAIBullet();
			AIbullet.setY(AIbullet.getY() + AIbullet.getVelocityY());
		}
                
                if(AIStateH == AIstateH_START) {
			resetAIBulletH();
		}
                else if(AIStateH == AIstateH_MOVING) {
			
			if(AIBulletHorizontal.getX() + AIBulletHorizontal.getVelocityX() >= MAXWIDTH) {
				
				resetAIBulletH();
			}
			else if(AIBulletHorizontal.getX() + AIBulletHorizontal.getVelocityX() < -50)
				
                                resetAIBulletH();
			AIBulletHorizontal.setX(AIBulletHorizontal.getX() + AIBulletHorizontal.getVelocityX());
                        AIBulletHorizontal.setY(AIBulletHorizontal.getY() + AIBulletHorizontal.getVelocityY());
		}
	}
        
        public void drawAIBullet() {
		int x = AIbullet.getX();
		int y = AIbullet.getY();
		int w = AIbullet.getWidth();
		int h = AIbullet.getHeight();
		Ellipse2D bulletRec = new Ellipse2D.Double(x, y, w, h);
		graphics.setColor(AIbullet.getColour());
		graphics.fill(bulletRec);
                
                int xcoord = AIBulletHorizontal.getX();
		int ycoord = AIBulletHorizontal.getY();
		int width = AIBulletHorizontal.getWidth();
		int height = AIBulletHorizontal.getHeight();
		Ellipse2D hBulletRec = new Ellipse2D.Double(xcoord, ycoord, width, height);
		graphics.setColor(AIBulletHorizontal.getColour());
		graphics.fill(hBulletRec);
	}
        
	public void resetAIBullet() {
		AIbullet.setX(randInt(player.getX() - 30, player.getX() + 30));
		AIbullet.setY(-50);
		AIbullet.setVelocityX(0);
		AIbullet.setVelocityY(20);
                if(keysPressed[KEY_ENTER])
                    AIState = AIstate_MOVING;
                else
                    AIState = AIstate_START;
	}
        
	public void resetAIBulletH() {
		AIBulletHorizontal.setY(randInt(player.getY() - 30, player.getY() + 30));
		AIBulletHorizontal.setX(-50);
		AIBulletHorizontal.setVelocityX(20);
		AIBulletHorizontal.setVelocityY(randInt(-7, 7));
                if(keysPressed[KEY_ENTER])
                    AIStateH = AIstateH_MOVING;
                else
                    AIStateH = AIstateH_START;
	}
        
	public void checkForCollisions() {
		checkForPlayerCollision();
		checkForEnemyCollision();
	}
	
	public void checkForPlayerCollision() {
            //REQUIRES: Player and bullet objects must exist
            //MODIFIES: lives
            //EFFECTS: Checks for overlap between boundaries of player and bullets, and changes lives accordingly
		Rectangle2D pRec = player.getBoundaryRectangle();
		Point2D AIBulletCentre = new Point2D.Double(AIbullet.getX(), AIbullet.getY());
                Point2D AIBulletHCentre = new Point2D.Double(AIBulletHorizontal.getX(), AIBulletHorizontal.getY());
		
		if(pRec.contains(AIBulletCentre)){
			lives--;
                     
                        resetAIBullet();    
                }
                
                if(pRec.contains(AIBulletHCentre)){
                        lives--;
                        resetAIBulletH();
                }
                    
	}
	
	public void checkForEnemyCollision() {
            //REQUIRES: Player and bullet objects must exist
             
            //EFFECTS: Checks for overlap between boundaries of enemy and bullet, and calls methods if the two do intersect
	
		Rectangle2D bulletBounds = playerBullet.getBoundaryRectangle();
		for(int col=0; col < enemyGrid.length; col++) {
			for(int row=0; row < enemyGrid[col].length; row++) {
				if(enemyGrid[col][row] != null && enemyGrid[col][row].isAlive()) {
					Rectangle2D enemyBounds = enemyGrid[col][row].getBoundaryRectangle();
					
					if(enemyBounds.intersects(bulletBounds)) {
						EnemyLocationWithBullet(enemyGrid[col][row]);
						updateScore();
					}
				}
			}
		}
	}
	
	public void EnemyLocationWithBullet(Enemy currentEnemy) {
            //REQUIRES: Player and bullet objects must exist
            //MODIFIES: colour, isAlive
            //EFFECTS: Changes higher level enemies to lower level enemies by changing colour, 
            //and when enemy is totally dead changes isAlive. Checks intersection and calls for bullet to be reset.
		if(currentEnemy.getColour() == Color.red)
                    currentEnemy.setColour(Color.blue);

                else if(currentEnemy.getColour() == Color.blue)
                    currentEnemy.setColour(Color.green);
                else if(currentEnemy.getColour() == Color.green)
                    currentEnemy.setAlive(false);
		Rectangle2D top = currentEnemy.getTopBounds();
		Rectangle2D bottom = currentEnemy.getBottomBounds();
		Rectangle2D left = currentEnemy.getLeftBounds();
		Rectangle2D right = currentEnemy.getRightBounds();

		Rectangle2D ballBounds = currentEnemy.getBoundaryRectangle();
		if(ballBounds.intersects(top))

                        resetBullet();
		
	}
	
	public void updateScore() {
            //MODIFIES: score
            //EFFECTS: Adds to score
		score += 100;
	}

	public void drawScore() {
            //REQUIRES: score to be a non-negative integer
            //EFFECTS: takes score value and outputs onto screen with graphics
		String text = "Score: " + score;
		Font impact = new Font("Impact", Font.PLAIN, 30);	//creates a font object
		
		AttributedString as = new AttributedString(text); //used to add attributes to any part of a given string
		as.addAttribute(TextAttribute.FONT, impact); //sets the font of the entire string to courier
		as.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 6); //sets the colour of characters from position 3 to 4 to red
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 6, text.length()); //sets the colour of characters from position 18 to the end to white
		
		graphics.drawString(as.getIterator(), MAXWIDTH-150, 45);
	}
        
        public void drawLives(){
            //REQUIRES: lives to exist
            //EFFECTS: takes lives value and outputs onto screen with graphics
                String text = "Lives: " + lives;
		Font impact = new Font("Impact", Font.PLAIN, 30);	//creates a font object
		
		AttributedString as = new AttributedString(text); //used to add attributes to any part of a given string
		as.addAttribute(TextAttribute.FONT, impact); //sets the font of the entire string to courier
		as.addAttribute(TextAttribute.FOREGROUND, Color.red, 0, 6); //sets the colour of characters from position 3 to 4 to red
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 6, text.length()); //sets the colour of characters from position 18 to the end to white
		
		graphics.drawString(as.getIterator(), 5, 45);
        }
        
        public boolean winConditionReached() {
            //REQUIRES: enemyGrid to exist
            //EFFECTS: returns true or false based on remaining enemies
		for(int i=0; i < enemyGrid.length; i++) {
			for(int j=0; j< enemyGrid[i].length; j++) {
				if(enemyGrid[i][j] != null && enemyGrid[i][j].isAlive())
					return false;
			}
		}
		return true;
	}
        
        public void drawLoseScreen() {
            //EFFECTS: draws lose message and prompt to play again
                drawBackground();
                String text = "You lose! Press SPACE to play again!";
		Font courier = new Font("Impact", Font.BOLD, 50);	//creates a font object
		
		AttributedString as = new AttributedString(text); //used to add attributes to any part of a given string
		as.addAttribute(TextAttribute.FONT, courier); //sets the font of the entire string to courier
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, text.length()); //sets the colour of characters from position 3 to 4 to red
		
		graphics.drawString(as.getIterator(), MAXWIDTH/2 - 400, MAXHEIGHT/2);
	}
        
        public void drawWinScreen() {
            //REQUIRES: score must exist
            //EFFECTS: displays score and win message with prompt to play again
                drawBackground();
                String text = "You Win! Your Score:" + score; 
                String text2 = "Press SPACE to play again!";
		Font courier = new Font("Impact", Font.BOLD, 50);	//creates a font object
		
		AttributedString as = new AttributedString(text); //used to add attributes to any part of a given string
                AttributedString as1 = new AttributedString(text2);
		as.addAttribute(TextAttribute.FONT, courier); //sets the font of the entire string to courier
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, text.length()); //sets the colour of characters from position 3 to 4 to red
		as1.addAttribute(TextAttribute.FONT, courier); //sets the font of the entire string to courier
		as1.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, text2.length()); //sets the colour of characters from position 3 to 4 to red
		graphics.drawString(as.getIterator(), MAXWIDTH/2 - 200, MAXHEIGHT/2);
                graphics.drawString(as1.getIterator(), MAXWIDTH/2 - 200, MAXHEIGHT/2 + 150);
	}
        
        public void drawStartScreen() {
            //EFFECTS: draws instructions onto screen
                String text = "Press SPACE to play! Arrow keys to move, space to shoot";
		Font courier = new Font("Impact", Font.BOLD, 20);	//creates a font object
		
		AttributedString as = new AttributedString(text); //used to add attributes to any part of a given string
                
		as.addAttribute(TextAttribute.FONT, courier); //sets the font of the entire string to courier
		as.addAttribute(TextAttribute.FOREGROUND, Color.white, 0, text.length()); //sets the colour of characters from position 3 to 4 to red
		
		graphics.drawString(as.getIterator(), MAXWIDTH/2 - 200, MAXHEIGHT - 20);
                
	}
	
	//creates an image for double buffering
	public void createImage() {
		if (image == null) {
			image = createImage(MAXWIDTH, MAXHEIGHT);
			
			if (image == null) {
				System.out.println("Cannot create buffer");
				return;
			}
			else
				graphics = (Graphics2D)image.getGraphics(); //get graphics object from Image
		}
	}
	
	//outputs everything to the JPanel
	public void drawImage() {
		Graphics g;
		try {
			g = this.getGraphics(); //a new image is created for each frame, this gets the graphics for that image so we can draw on it
			if (g != null && image != null) {
				g.drawImage(image, 0, 0, null);
				g.dispose(); //not associated with swing, so we have to free memory ourselves (not done by the JVM)
			}
			image = null;
		}catch(Exception e) {System.out.println("Graphics objects error");}
	}  
	//KEYLISTENER METHODS
	public void keyPressed(KeyEvent e) {
            //MODIFIES: keysPressed, bulletState
            //EFFECTS: based on keyboard input changes values in keysPressed array and also sets state of bullet
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			keysPressed[KEY_LEFT] = true;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			keysPressed[KEY_RIGHT] = true;
                else if(e.getKeyCode() == KeyEvent.VK_UP)
			keysPressed[KEY_UP] = true;
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			keysPressed[KEY_DOWN] = true;
		else if(bulletState == BULLET_START && e.getKeyCode() == KeyEvent.VK_SPACE) {
			keysPressed[KEY_SPACE] = true;
			bulletState = BULLET_MOVING;
                        keysPressed[KEY_ENTER] = true;    
                }
			
	}

	public void keyReleased(KeyEvent e) {
            //EFFECTS: checks for key being released
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			keysPressed[KEY_LEFT] = false;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			keysPressed[KEY_RIGHT] = false;
                else if(e.getKeyCode() == KeyEvent.VK_UP)
			keysPressed[KEY_UP] = false;
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			keysPressed[KEY_DOWN] = false;
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			keysPressed[KEY_SPACE] = false;   
                }
	}
	
	public void keyTyped(KeyEvent e) {}
        
        public static int randInt(int min, int max) {
            //REQUIRES: minimum value less than or equal to max value
            //EFFECTS: generates and returns a random number

            Random rand = new Random();

            int randomNum = rand.nextInt((max - min) + 1) + min;

            return randomNum;
        }
        
}