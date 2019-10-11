package com.openclassrooms.realestatemanager.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 18/09/2019.
 */
@Entity
data class RealEstate(@PrimaryKey val id: Int,
                      @ColumnInfo(name="type") var type: String = "house",
                      @ColumnInfo(name="price") var price: Price = Price(0,false),
                      @ColumnInfo(name="surface") var surface: Int = 0,
                      @ColumnInfo(name="room")var room: Int = 0,
                      @ColumnInfo(name="description") var description: String = "no description yet",
                      @ColumnInfo(name="address") var address: Address = Address("nowhere",city = "somewhere", country = "elsewere"),
                      @ColumnInfo(name="Interess") val Interess: ArrayList<Interess>,
                      @ColumnInfo(name="statut") var statut: String = "unknown",
                      @ColumnInfo(name="date_entree") var dateEntree: String = "unknown",
                      @ColumnInfo(name="date_vente") var dateVente: String = "unknown",
                      @ColumnInfo(name="agent") var agent: Agent = Agent(0,"No one","no"),
                      @ColumnInfo(name="imageList") val imageList: ArrayList<String>) : Serializable {
}