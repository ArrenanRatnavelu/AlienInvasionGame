
package coe528.project;

/**
 *
 * @author Arrenan
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
//OVERVIEW: Mutable, creates Player object for gameplay -  uses inherited methods 
public class Player extends Entity{
    
    //AF(c) = {player| player x, y are any integer, height & width > 0, colour is any Color}
    
    //Rep Invariant: player.height > 0, player.width > 0 & player.colour != null
	private int x, y;
	private int velX;
        private int velY;
	private int height, width;
	private Color colour;
	
	public Player(int x, int y, int height, int width, Color colour) {
		super(x, y, height, width, colour);
                
	}
        
        public String toString(){
            //EFFECTS: Returns a string with information about class
            String s = "Player coordinates (x,y): (" + this.getX() + "," + this.getY() + ") -- Player Height: " + this.getHeight() + " -- Player Width: " + this.getWidth(); 
                    return s;
        }
        
        public boolean repOk(){
            //EFFECTS: Returns a boolean based on if rep invariant is met
            if(this.width < 0)
                return false;
            if(this.height < 0)
                return false;
            if(this.colour == null)
                return false;
        return true;    
            
        }
                
}

