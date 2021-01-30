/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gameofthreads.project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TODO: this class can be deleted once we have actual classes/tests
 * 
 * To run this class, right click the source file that the tests are made for 
 * (ExampleClass.java, in this case), and select "Test File". 
 */
public class ExampleClassTest {
    
    ExampleClass ex;
    
    // Runs before each unit test; don't have to create ExampleClass object everytime
    @Before
    public void init() throws Exception {
       ex = new ExampleClass();
    }


    @Test
    public void testAddTwoNums_twoPlusTwoEqualsFour() {        
        int expected = 4;
        int actual = ex.addTwoInts(2, 2);
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testAddTwoNums_threePlusFiveNotEqualFour() {        
        int expected = 4;
        int actual = ex.addTwoInts(3, 5);
        assertNotEquals(expected, actual);
    }
    
    @Test
    public void testCheckNumsAreSame_threeNotEqualToFour() {        
        boolean areSame = ex.checkNumsAreSame(3, 4);
        assertFalse(areSame);
    }
    
    @Test
    public void testExampleVarIsUnchanged() {
        assertEquals("some string", ex.exampleVar);
    }
    
}
