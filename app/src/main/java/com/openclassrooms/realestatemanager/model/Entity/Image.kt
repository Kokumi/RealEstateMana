package com.openclassrooms.realestatemanager.model.Entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.net.URI

/**
 * Created by Debruyckère Florian on 05/11/2019.
 */
@Entity(tableName = "Image",
        foreignKeys= arrayOf(
                ForeignKey(
                        entity = RealEstate::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("id"))
                )
        )
class Image( val id : Int,
            @PrimaryKey val Uri : String)