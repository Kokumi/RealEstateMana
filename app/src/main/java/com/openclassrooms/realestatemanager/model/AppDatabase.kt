package com.openclassrooms.realestatemanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Entity.Address
import com.openclassrooms.realestatemanager.model.Entity.Image
import com.openclassrooms.realestatemanager.model.Entity.Price
import com.openclassrooms.realestatemanager.model.Entity.RealEstate

/**
 * Created by Debruyckère Florian on 10/10/2019.
 */
@Database(entities = [RealEstate::class, Price::class, Address::class, Image::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun realEstateDao(): RealEstateDao

    fun getDatabase(pContext: Context): AppDatabase?{
        var INSTANCE : AppDatabase? = null
        if(INSTANCE == null){
            synchronized(AppDatabase::class){
                INSTANCE= Room.databaseBuilder(pContext.applicationContext, AppDatabase::class.java, "database-name").build()
            }
        }
        return INSTANCE
    }
}