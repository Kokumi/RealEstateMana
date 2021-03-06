package com.openclassrooms.realestatemanager.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 19/09/2019.
 */
@Entity(tableName = "Agent")
class Agent(@PrimaryKey(autoGenerate = true) val id: Int = 0,
            @ColumnInfo(name = "name") var name: String) : Serializable