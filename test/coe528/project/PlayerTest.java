/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.awt.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Arrenan
 */
public class PlayerTest {
    
    public PlayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Player.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Player instance = new Player(25, 25, 25, 30, Color.red);
        String expResult = "Player coordinates (x,y): (25,25) -- Player Height: 25 -- Player Width: 30";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of repOK method, of class Player.
     */
    @Test
    public void testRepOk() {
        System.out.println("repOk");
        Player instance = new Player(0, 0, 20, 70, null);
        boolean expResult = false;
        boolean result = instance.repOk();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testInvalidConstructor(){
        System.out.println("InvalidConstructor");
        boolean gotEx = false;
        try{
            Player instance = new Player(0, 0, 0, 30, Color.blue);
        }
        catch(IllegalArgumentException e){
            gotEx = true;
            
        }
        assertTrue(gotEx);
              
    }
    
}
