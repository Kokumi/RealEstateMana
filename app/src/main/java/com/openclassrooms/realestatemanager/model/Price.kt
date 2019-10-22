package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 27/09/2019.
 */
@Entity(tableName = "Price")
class Price(@PrimaryKey val id : Int,
            @ColumnInfo(name = "value") val value: Int,
            @ColumnInfo(name = "isDollar") val isDollar: Boolean) : Serializable