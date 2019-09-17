package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void convertPriceTest1(){
        int toTest = Utils.convertDollarToEuro(10,false);

        assertEquals(8,toTest);
    }
    @Test
    public void convertPriceTest2(){
        int toTest = Utils.convertDollarToEuro(10,true);

        assertEquals(11,toTest);
    }
}