package com.openclassrooms.realestatemanager.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Debruyckère Florian on 10/10/2019.
 */
@Dao
interface RealEstateDao {
    @Query("SELECT * FROM realestate")
    fun getAll():List<RealEstate>

    @Query("SELECT * FROM realestate WHERE id IN (:pId)")
    fun loadAllById(pId : IntArray) : List<RealEstate>

    @Insert
    fun insertAll(vararg realEstates : RealEstate)

}