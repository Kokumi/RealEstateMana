package com.openclassrooms.realestatemanager.model

import androidx.room.*
import com.openclassrooms.realestatemanager.model.Entity.*

/**
 * Created by Debruyckère Florian on 10/10/2019.
 */
@Dao
interface RealEstateDao {
    @Query("SELECT * FROM realestate")
    fun getAll():List<RealEstate>

    @Query("SELECT * FROM realestate WHERE id IN (:pId)")
    fun loadAllById(pId : IntArray) : List<RealEstate>

    @Query("SELECT * FROM Price WHERE id == (:pId)")
    fun getPriceById(pId: Int) : Price

    @Query("SELECT * FROM Address WHERE id == (:pId)")
    fun getAddressById(pId: Int) : Address

    @Query("SELECT * FROM Address")
    fun getAllAddress(): List<Address>

    @Query("SELECT * FROM Image WHERE id== (:pId)")
    fun getImageOfEstate(pId: Int): List<Image>

    @Query("SELECT * FROM Agent WHERE id== (:pId)")
    fun getAgent(pId: Int): Agent

    @Query("SELECT * FROM Agent WHERE name== (:pName)")
    fun getAgentByName(pName: String): Agent?

    @Query("SELECT * FROM Interess WHERE id== (:pId)")
    fun getInteressByID(pId: Int): Interess

    @Insert
    fun insertEstate(realEstates : RealEstate)

    @Insert
    fun insertPrice(pPrice: Price): Long

    @Insert
    fun insertAddress(pAddress: Address): Long

    @Insert
    fun insertImage(pImage: Image)

    @Insert
    fun insertAgent(pAgent : Agent) : Long

    @Transaction
    fun insertNewRealEstate(pRealEstate: RealEstate, pPrice: Price, pAddress: Address, pAgent: Agent){
        val priceId = insertPrice(pPrice)
        val addressId = insertAddress(pAddress)
        val agentId : Int = if(getAgentByName(pAgent.name) != null) getAgentByName(pAgent.name)!!.id else insertAgent(pAgent).toInt()

        pRealEstate.agentId = agentId
        pRealEstate.priceId = priceId.toInt()
        pRealEstate.addressId = addressId.toInt()
        insertEstate(pRealEstate)
    }

    @Insert
    fun insertInterest(pInterest : Interess) : Long

    @Update
    fun updatePrice(pPrice: Price):Int

    @Update
    fun updateAddress(pAddress: Address):Int

    @Update
    fun updateEstate(pRealEstate: RealEstate)

    @Transaction
    fun updateRealEstate(pRealEstate: RealEstate, pPrice: Price, pAddress: Address, pAgent: Agent){
        val priceId = updatePrice(pPrice)
        val addressId = updateAddress(pAddress)
        val agentId : Int = if(getAgentByName(pAgent.name) != null) getAgentByName(pAgent.name)!!.id else insertAgent(pAgent).toInt()

        pRealEstate.agentId = agentId
        pRealEstate.addressId=addressId
        pRealEstate.priceId=priceId
        updateEstate(pRealEstate)
    }

}