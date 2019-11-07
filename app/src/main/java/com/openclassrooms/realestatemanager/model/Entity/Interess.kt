package com.openclassrooms.realestatemanager.model.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 19/09/2019.
 */
@Entity(tableName = "Interess")
class Interess(@PrimaryKey(autoGenerate = true) val id: Int,
               @ColumnInfo(name = "name") var name: String) : Serializable