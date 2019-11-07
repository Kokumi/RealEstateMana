package com.openclassrooms.realestatemanager.model

import androidx.room.*
import com.openclassrooms.realestatemanager.model.Entity.Address
import com.openclassrooms.realestatemanager.model.Entity.Image
import com.openclassrooms.realestatemanager.model.Entity.Price
import com.openclassrooms.realestatemanager.model.Entity.RealEstate

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

    @Query("SELECT * FROM Image WHERE id== (:pId)")
    fun getImageOfEstate(pId: Int): List<Image>

    @Insert
    fun insertEstate(realEstates : RealEstate)

    @Insert
    fun insertPrice(pPrice: Price): Long

    @Insert
    fun insertAddress(pAddress: Address): Long

    @Insert
    fun insertImage(pImage: Image)

    @Transaction
    fun insertNewRealEstate(pRealEstate: RealEstate, pPrice: Price, pAddress: Address){
        val priceId = insertPrice(pPrice)
        val addressId = insertAddress(pAddress)
        pRealEstate.priceId = priceId.toInt()
        pRealEstate.addressId = addressId.toInt()
        insertEstate(pRealEstate)
    }

    @Update
    fun updatePrice(pPrice: Price):Int

    @Update
    fun updateAddress(pAddress: Address):Int

    @Update
    fun updateEstate(pRealEstate: RealEstate)

    @Transaction
    fun updateRealEstate(pRealEstate: RealEstate, pPrice: Price, pAddress: Address){
        val priceId = updatePrice(pPrice)
        val addressId = updateAddress(pAddress)
        pRealEstate.addressId=addressId
        pRealEstate.priceId=priceId
        updateEstate(pRealEstate)
    }

}