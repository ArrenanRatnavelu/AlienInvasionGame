package coe528.project;

import coe528.project.Enemy;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//OVERVIEW: Immutable, loads file with enemy information and creates a 2D array and stores Enemy objects inside array to be displayed by game
public class LevelLoader {
    
    //AF(c) = {enemyGrid | enemyGrid has 16 columns and a maximum of 8 rows - x = 16, lineNumber < 8}
    //Rep Invariant: lines != null, lineNumber < 8
    
	private Scanner fileInput;
	private Vector<String> lines = new Vector<String>();
	private Enemy[][] enemyGrid;
	
	public void openLevelFile(String fileName) {
            //REQUIRES: File must be in correct directory
            //EFFECTS: takes filename and attempts to open and scan file. If file cannot be found throws exception
		fileInput = null;
    	try {
    		fileInput = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("No such file exists! Add the file to the project OR check to see that the file name is correct.");
		}
	}
	
	public Enemy[][] getEnemyGrid() {
            //REQUIRES: enemyGrid must be initialized
            //EFFECTS: Returns enemyGrid
		return enemyGrid;
	}
	
	public void createLevelGrid() {
            //REQUIRES: fileInput must have a file that exists
            //EFFECTS: Reads each line of file and checks if file meets length criteria - if not, throws exception. If met, creates a grid to store enemies in level 
		while(fileInput.hasNext())
			lines.add(fileInput.nextLine());
		
		int y = lines.size();
                if(y > 8)
                    throw new IllegalArgumentException("Number of lines needs to be 8 or less");
                System.out.println(lines.size());
		int x = 16;
		enemyGrid = new Enemy[x][y];
		
		for(int lineNumber=0; lineNumber < lines.size(); lineNumber++) {
			createRowEnemies(lines.elementAt(lineNumber), lineNumber);
		}
                closeFile();
	}
	
	private void createRowEnemies(String line, int y) {
            //REQUIRES: A string containing enemy information and an integer to denote line number
            //EFFECTS: If line is too long, throws exception, otherwise calls methods to create enemy for each character in the line of the file.
                if(line.length() > 16)
                    throw new IllegalArgumentException("Number of characters in a row must be 16 or less");
		for(int x=0; x < line.length(); x++) {
                    
			Color enemyColour = chooseEnemyColour(line.charAt(x));
			createEnemy(x, y, enemyColour);
		}
	}
	
	private Color chooseEnemyColour(char enemyColour) {
            //REQUIRES: a character that specifies colour of enemy
            //EFFECTS: Returns a color based on the character at specific index of the line
		if(enemyColour == 'R')
                        return Color.red;
		else if(enemyColour == 'G')
			return Color.green;
		else if(enemyColour == 'B')
			return Color.blue;
		else
			return null;
	}
	
	private void createEnemy(int x, int y, Color enemyColour) {
            //REQUIRES: two integers to specify position of enemy, one Color to specify colour
            //EFFECTS: creates a new Enemy with given parameters and stores in corresponding index of enemyGrid
		if(enemyColour == null)
			enemyGrid[x][y] = null;
		
                else{
                        if(x == 0){
			enemyGrid[x][y] = new Enemy(64*x + 20, 30*y + 100, enemyColour);
                        }
                        else
                            enemyGrid[x][y] = new Enemy(64*x + 20, 30*y + 100, enemyColour);
                            
                }
    }
		
	public void closeFile() {
            //EFFECTS: closes fileInput scanner
		fileInput.close();
	}
        
        public String toString(){
            //EFFECTS: Returns a string with information about class
            String s = "Enemy rows: " + (lines.size() - 1) + " rows"; 
                    return s;
        }
        
        public boolean repOk(){
            //EFFECTS: Returns a boolean based on if rep invariant is met
            if(lines.size() > 8 )
                return false;
            if(lines == null)
                return false;
        return true;    
            
        }
}