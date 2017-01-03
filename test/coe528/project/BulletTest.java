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
public class BulletTest {
    
    public BulletTest() {
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
     * Test of toString method, of class Bullet.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Bullet instance = new Bullet(0, 0, 50, 60, Color.white);
        String expResult = "Player bullet coordinates (x,y): (0,0) -- Bullet Height: 50 -- Bullet Width: 60";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of repOK method, of class Bullet.
     */
    @Test
    public void testRepOk() {
        System.out.println("repOk");
        Bullet instance = new Bullet(0, 0, 20, 70, Color.BLACK);
        boolean expResult = true;
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
            Bullet instance = new Bullet(0, 0, 1, -300, Color.blue);
        }
        catch(IllegalArgumentException e){
            gotEx = true;
            
        }
        assertTrue(gotEx);
              
    }
    
}
