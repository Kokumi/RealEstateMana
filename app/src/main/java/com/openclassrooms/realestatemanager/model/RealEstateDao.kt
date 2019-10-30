package com.openclassrooms.realestatemanager.model

import androidx.room.*

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

    @Insert
    fun insertEstate(realEstates : RealEstate)

    @Insert
    fun insertPrice(pPrice: Price): Long

    @Insert
    fun insertAddress(pAddress: Address): Long

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
    fun updateRealEstate(pRealEstate: RealEstate,pPrice: Price,pAddress: Address){
        val priceId = updatePrice(pPrice)
        val addressId = updateAddress(pAddress)
        pRealEstate.addressId=addressId
        pRealEstate.priceId=priceId
        updateEstate(pRealEstate)
    }

}