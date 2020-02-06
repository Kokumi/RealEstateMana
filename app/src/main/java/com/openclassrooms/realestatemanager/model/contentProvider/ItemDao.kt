package com.openclassrooms.realestatemanager.model.contentProvider

import androidx.room.Dao
import android.database.Cursor
import androidx.room.Query

/**
 * Created by Debruyckère Florian on 20/01/2020.
 */
@Dao
interface ItemDao {
    //@Query("Select * From RealEstate Where id == :pId ")
    //fun getItemWithCursor(pId : Int) : Cursor
}