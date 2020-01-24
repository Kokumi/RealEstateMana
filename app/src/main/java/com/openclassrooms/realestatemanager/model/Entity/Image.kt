package com.openclassrooms.realestatemanager.model.Entity

import android.content.ContentValues
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
class Image( var id : Int ,
            @PrimaryKey var Uri : String ){



    fun fromContentValues(pValues : ContentValues) : Image{
        var iId = 0
        var iUri = ""

        if(pValues.containsKey("id")){ iId=pValues.getAsString("id").toInt() }
        if(pValues.containsKey("uri")){ iUri =pValues.getAsString("uri")}

        return Image(iId,iUri)

}}