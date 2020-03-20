package com.openclassrooms.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.model.AppDatabase;
import com.openclassrooms.realestatemanager.model.Entity.Image;
import com.openclassrooms.realestatemanager.model.Utils;
import com.openclassrooms.realestatemanager.model.contentProvider.ItemContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Currency;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.openclassrooms.go4lunch", appContext.getPackageName());
    }
    @Test
    public void ConnectionCheckTest(){
        assertTrue(Utils.isInternetAvailable(InstrumentationRegistry.getTargetContext()));
    }

    private ContentResolver mContentResolver;

    private static long USER_ID = 1;


    @Before
    public void setUp(){
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void insertAndGetItem(){
        /*final Uri userUri = mContentResolver.insert(
                Uri.parse("content://com.openclassrooms.realestatemanager.provider/"+ Image.class.getSimpleName()),
                generateItem());*/

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(
                Uri.parse("content://com.openclassrooms.realestatemanager.provider/"+ Image.class.getSimpleName()), USER_ID),
                null,null,null,null);

        //assertThat(cursor, );
        assertTrue(cursor.moveToFirst());
        //assertSame("some",cursor.getString(cursor.getColumnIndexOrThrow("uri")));
        assertFalse(cursor.isNull(1));
    }


    private ContentValues generateItem(){
        final ContentValues values = new ContentValues();
        values.put("id","1");
        values.put("uri","some");

        return values;
    }


}
