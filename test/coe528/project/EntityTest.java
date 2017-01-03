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
public class EntityTest {
    
    public EntityTest() {
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
     * Test of repOk method, of class Entity.
     */
    @Test
    public void testRepOk() {
        System.out.println("repOk");
        Entity instance = new Entity(0, 0, 20, 70, null);
        boolean expResult = false;
        boolean result = instance.repOk();
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
        Entity instance = new Entity(0, 0, 20, 70, Color.BLACK);
        String expResult = "Entity coordinates (x,y): (0,0) -- Entity Height: 20 -- Entity Width: 70";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testInvalidConstructor(){
        System.out.println("InvalidConstructor");
        boolean gotEx = false;
        try{
            Entity instance = new Entity(0, 0, -20, 30, Color.blue);
        }
        catch(IllegalArgumentException e){
            gotEx = true;
            
        }
        assertTrue(gotEx);
              
    }

    
    
    

    
}
