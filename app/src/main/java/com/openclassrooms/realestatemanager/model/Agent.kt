package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 19/09/2019.
 */
@Entity(tableName = "Agent")
class Agent(@PrimaryKey(autoGenerate = true) val id: Int,
            @ColumnInfo(name = "name") val name: String,
            @ColumnInfo(name= "telephone") var telephone: String) : Serializable