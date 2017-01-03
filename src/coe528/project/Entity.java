package coe528.project;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//OVERVIEW: Mutable, creates Entity object for gameplay. Allows for entity attributes to be changed and returns attribute values if getter methods called. 
public class Entity {
    //AF(c) = {entity| entity x, y are any integer, height & width are any integer > 0, colour is any Color}
    
    //Rep Invariant: height > 0, width > 0, colour != null
	private int x, y;
	private int velX, velY;
	private int height, width;
        private final int HEIGHT = 56, WIDTH = 56;
	private Color colour;
	
	//constructor
	public Entity(int x, int y, int height, int width, Color colour) {
                if(height <= 0)
                    throw new IllegalArgumentException("height cannot be zero or negative");
                if(width <= 0)
                    throw new IllegalArgumentException("width cannot be zero or negative");
		this.x = x;
		this.y = y;
		this.velX = 0;
		this.velY = 0;
		this.height = height;
		this.width = width;
		this.colour = colour;
	}
        
        public Entity(int x, int y, Color colour) {
		this.x = x;
		this.y = y;
		this.height = HEIGHT;
		this.width = WIDTH;
		this.colour = colour;
	}
	public Rectangle2D getBoundaryRectangle() {
            //REQUIRES: x, y, height, width must exist
            //EFFECTS: returns a new boundary rectangle based on given parameters
		int x = getX() + (int)(getWidth()*0.15);
		int y = getY() + (int)(getHeight()*0.15);
		int w = (int)(getWidth()*0.70);
		int h = (int)(getHeight()*0.70);
		return new Rectangle2D.Double(x, y, w, h);
	}
	
	//REQUIRES: x, y, velx, velY, height, width, colour to be initialized
        //MODIFIES: x, y, velx, vely, height, width, or colour
        //EFFECTS: sets and returns values of attributes of object
	public int getX() {return this.x;}
	public void setX(int x) {this.x = x;}
	
	public int getY() {return this.y;}
	public void setY(int y) {this.y = y;}
	
	public int getVelocityX() {return this.velX;}
	public void setVelocityX(int velX) {this.velX = velX;}
	
	public int getVelocityY() {return this.velY;}
	public void setVelocityY(int velY) {this.velY = velY;}

	public int getHeight() {return this.height;}
	public void setHeight(int height) {this.height = height;}
	
	public int getWidth() {return this.width;}
	public void setWidth(int width) {this.width = width;}
	
	public Color getColour() {return this.colour;}
	public void setColour(Color c) {this.colour = c;}
        
       public String toString(){
           //EFFECTS: Returns a string with information about class
            String s = "Entity coordinates (x,y): (" + x + "," + y + ") -- Entity Height: " + height + " -- Entity Width: " + width; 
                    return s;
        }
        
        public boolean repOk(){
            //EFFECTS: Returns a boolean based on if rep invariant is met
            if(this.getWidth() < 0)
                return false;
            if(this.getHeight() < 0)
                return false;
            if(this.getColour() == null)
                return false;
        return true;    
           
        }
}
