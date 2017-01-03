package coe528.project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//OVERVIEW: Mutable, creates Enemy object for gameplay -  uses inherited methods 
public class Enemy extends Entity {
    
    //AF(c) = {enemy| enemy x, y are any integer, height & width are fixed integers, colour is any Color}
    //Rep Invariant: enemy.colour != null
    
	private int x, y;
	private boolean isAlive;
	private Color colour;
	
	public Enemy(int x, int y, Color colour) {
                
		super(x, y, colour);
		isAlive = true;
	}

	public Rectangle2D getBoundaryRectangle() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}
	
	public Rectangle2D getTopBounds() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), 1);
	}
	
	public Rectangle2D getBottomBounds() {
		return new Rectangle2D.Double(getX(), getY()+getHeight(), getWidth(), 1);
	}
	
	public Rectangle2D getLeftBounds() {
		return new Rectangle2D.Double(getX(), getY(), 1, getHeight());
	}
	
	public Rectangle2D getRightBounds() {
		return new Rectangle2D.Double(getX()+getWidth(), getY(), 1, getHeight());
	}
	
	
	
	public boolean isAlive() {return this.isAlive;}
	public void setAlive(boolean isAlive) {this.isAlive = isAlive;}
        
        public String toString(){
            //EFFECTS: Returns a string with information about class
            String s = "Current enemy coordinates (x,y): (" + this.getX() + "," + this.getY() + ")"; 
                    return s;
        }
        
        public boolean repOk(){
            //EFFECTS: Returns a boolean based on if rep invariant is met
            if(colour == null)
                return false;
        return true;    
            
        }
        
        
}
