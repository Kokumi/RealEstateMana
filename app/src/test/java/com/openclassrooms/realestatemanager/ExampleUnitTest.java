package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.model.Utils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    @Test
    public void DateTranslate(){
        String toTest = Utils.getTodayDate();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        String exemple = dateFormat.format(new Date());

        assertEquals(exemple,toTest);
    }

    @Test
    public void powerTest(){
        double result = Math.pow(2.0,2.0);
        assertEquals(4.0,result,0.0);
    }
}