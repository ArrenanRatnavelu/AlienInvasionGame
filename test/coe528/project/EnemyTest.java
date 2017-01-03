/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
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
public class EnemyTest {
    
    public EnemyTest() {
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
     * Test of isAlive method, of class Enemy.
     */
    @Test
    public void testIsAlive() {
        System.out.println("isAlive");
        Enemy instance = new Enemy(0, 20, Color.green);
        boolean expResult = true;
        boolean result = instance.isAlive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Enemy.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Enemy instance = new Enemy(0, 20, Color.BLACK);
        String expResult = "Current enemy coordinates (x,y): (0,20)";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of repOk method, of class Enemy.
     */
    @Test
    public void testRepOk() {
        System.out.println("repOk");
        Enemy instance = new Enemy(0, 1, null);
        boolean expResult = false;
        boolean result = instance.repOk();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
