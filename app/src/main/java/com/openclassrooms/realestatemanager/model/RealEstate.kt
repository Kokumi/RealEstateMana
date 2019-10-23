package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
                        childColumns = arrayOf("priceId")
                )
        ))
data class RealEstate(@PrimaryKey val id: Int,
                      @ColumnInfo(name="type") var type: String = "house",
                      @ColumnInfo(name="surface") var surface: Int = 0,
                      @ColumnInfo(name="room") var room: Int = 0,
                      @ColumnInfo(name="description") var description: String = "no description yet",
                       //val Interess: ArrayList<Interess>,
                      @ColumnInfo(name="statut") var statut: String = "unknown",
                      @ColumnInfo(name="dateEntree") var dateEntree: String = "unknown",
                      @ColumnInfo(name="dateVente") var dateVente: String = "unknown",
                       //val imageList: ArrayList<String>,
                      @ColumnInfo(name="addressId") var addressId : Int =0,
                      @ColumnInfo(name="agentId") var agentId : Int = 0,
                      @ColumnInfo(name="priceId") var priceId : Int = 0) : Serializable
//var price: Price = Price(0,false)
//var address: Address = Address("nowhere",city = "somewhere", country = "elsewere"),
//var agent: Agent = Agent(0,"No one","no"),