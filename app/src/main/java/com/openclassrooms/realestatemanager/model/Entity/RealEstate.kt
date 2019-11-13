package com.openclassrooms.realestatemanager.model.Entity

import android.graphics.Bitmap
import androidx.room.*
import java.io.Serializable

/**
 * Created by Debruyckère Florian on 18/09/2019.
 */
@Entity(tableName = "RealEstate",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Address::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("addressId")),
                ForeignKey(
                        entity = Price::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("priceId")),
                ForeignKey(
                        entity = Agent::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("agentId")
                )
        ))
data class RealEstate(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                      @ColumnInfo(name="type") var type: String = "house",
                      @ColumnInfo(name="surface") var surface: Int = 0,
                      @ColumnInfo(name="room") var room: Int = 0,
                      @ColumnInfo(name="description") var description: String = "no description yet",
                      @ColumnInfo(name = "interess") var Interess: String = "",
                      @ColumnInfo(name="statut") var statut: String = "unknown",
                      @ColumnInfo(name="dateEntree") var dateEntree: String = "unknown",
                      @ColumnInfo(name="dateVente") var dateVente: String = "unknown",
                      @ColumnInfo(name="addressId") var addressId : Int =0,
                      @ColumnInfo(name="agentId") var agentId : Int = 0,
                      @ColumnInfo(name="priceId") var priceId : Int = 0
                      ) : Serializable