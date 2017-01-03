package coe528.project;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//OVERVIEW: Mutable, creates Bullet object for gameplay -  uses inherited methods 
public class Bullet extends Entity{
    
    //AF(c) = {bullet| x, y are any integer, height & width > 0, colour is any Color}
    
    //Rep Invariant: bullet.height > 0, bullet.width > 0 & bullet.colour != null
	private int x, y;
	private int velX, velY;
	private int height, width;
	private Color colour;
	
	//constructor
	public Bullet(int x, int y, int height, int width, Color colour) {
		
                super(x, y, height, width, colour);
	}
        
        public String toString(){
            //EFFECTS: Returns a string with information about class
            String s = "Player bullet coordinates (x,y): (" + this.getX() + "," + this.getY() + ") -- Bullet Height: " + this.getHeight() + " -- Bullet Width: " + this.getWidth(); 
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
