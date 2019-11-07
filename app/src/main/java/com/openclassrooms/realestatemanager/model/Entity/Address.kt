package com.openclassrooms.realestatemanager.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 27/09/2019.
 */
@Entity(tableName = "Address")
class Address(@PrimaryKey(autoGenerate = true) val id : Int = 0,
              @ColumnInfo(name = "address") var address : String,
              @ColumnInfo(name = "apartment") var apartment : String = "null",
              @ColumnInfo(name = "city") var city : String,
              @ColumnInfo(name = "country") var country: String) : Serializable