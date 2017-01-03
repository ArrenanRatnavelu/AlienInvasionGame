/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;
import coe528.project.AlienInvasion;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
//OVERVIEW: Immutable, calls LevelLoader to create levels, calls Bullet, Enemy, Player to create game entities, and does all graphics/display work. Also takes keyboard input to move player entity.
public class MainPanel extends JFrame implements WindowListener{
	private AlienInvasion con;
	
	public MainPanel() {
		Container content = getContentPane();
		con = new AlienInvasion(this); //creates a JPanel
		content.add(con, "Center"); //add JPanel to Container
		addWindowListener(this); //adds a listener to Container to check if it is altered
		
		pack();
		setVisible(true);
		setResizable(false);
	}

	//stops the game when the window is closed
	public void windowClosing(WindowEvent e) {
            //REQUIRES: AlienInvasion object to exist
            //EFFECTS: Calls stopGame method to end game when window is closing
		con.stopGame();
	}

	//declared, but unfilled methods from the WindowListener interface
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}

