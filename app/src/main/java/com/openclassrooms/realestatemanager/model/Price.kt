package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 27/09/2019.
 */
@Entity(tableName = "Price")
class Price(@PrimaryKey(autoGenerate = true) val id : Int = 0,
            @ColumnInfo(name = "value") var value: Int,
            @ColumnInfo(name = "isDollar") var isDollar: Boolean) : Serializable