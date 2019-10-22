package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 27/09/2019.
 */
@Entity(tableName = "Address")
class Address(@PrimaryKey val id : Int,
              @ColumnInfo(name = "address") val address : String,
              @ColumnInfo(name = "apartment") val apartment : String = "null",
              @ColumnInfo(name = "city") val city : String,
              @ColumnInfo(name = "country") val country: String) : Serializable