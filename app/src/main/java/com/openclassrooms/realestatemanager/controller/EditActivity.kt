package com.openclassrooms.realestatemanager.controller

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.room.Room
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Price
import com.openclassrooms.realestatemanager.model.RealEstate
import java.lang.Exception

class EditActivity : AppCompatActivity() {
    private var realEstateData : RealEstate? = null
    private var priceData : Price? = null
    private var addressData : Address? = null
    private var mExisting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realEstateData = if(intent.extras?.get("SELECTION") != null) intent.extras?.get("SELECTION") as RealEstate
        else null
        priceData = if(intent.extras?.get("SELECTION_PRICE") != null) intent.extras?.get("SELECTION_PRICE") as Price
        else null
        addressData = if(intent.extras?.get("SELECTION_ADDRESS") != null) intent.extras?.get("SELECTION_ADDRESS") as Address
        else null

        if(realEstateData!= null && priceData!=null && addressData!=null) mExisting = true


        display()
    }

    private fun display(){
        val rED = realEstateData
        val pD = priceData
        val aD = addressData

        val editType = findViewById<EditText>(R.id.edit_type)
        val editPrice = findViewById<EditText>(R.id.edit_price)
        val switchPriceType = findViewById<Switch>(R.id.edit_price_type)
        val editAddress = findViewById<EditText>(R.id.edit_address)
        val editCity = findViewById<EditText>(R.id.edit_city)
        val editCountry = findViewById<EditText>(R.id.edit_country)
        val editApartement = findViewById<EditText>(R.id.edit_apartment)
        val editPostal = findViewById<EditText>(R.id.edit_postalcode)
        val editDescription = findViewById<EditText>(R.id.edit_description)
        val editSurface = findViewById<EditText>(R.id.edit_surface)
        val finishButton = findViewById<Button>(R.id.edit_validation)

        if(realEstateData != null){
            editType.setText(rED?.type)
            editPrice.setText(pD!!.value.toString())
            editAddress.setText(aD!!.address)
            editCity.setText(aD.city)
            editCountry.setText(aD.country)
            editApartement.setText(aD.apartment)
            //editPostal.setText(addressData!!.postal)
            editDescription.setText(rED!!.description)
            editSurface.setText(rED.surface.toString())
            switchPriceType.isChecked = pD.isDollar
        }

        finishButton.setOnClickListener{
            if(priceData == null && realEstateData == null && addressData == null){
                priceData = Price(value= if(editPrice.text != null) Integer.parseInt( editPrice.text.toString()) else 0,
                        isDollar = switchPriceType.isChecked)

                addressData = Address(address = if(editAddress.text != null) editAddress.text.toString() else "",
                        apartment = if(editApartement.text != null) editApartement.text.toString() else "",
                        city = if(editCity.text != null) editCity.text.toString() else "" ,
                        country = if(editCountry.text != null) editCountry.text.toString() else "")

                realEstateData = RealEstate(type = if(editType.text != null) editType.text.toString() else "",
                        description = if(editDescription.text != null) editDescription.text.toString() else "",
                        surface = if(editSurface.text != null) Integer.parseInt(editSurface.text.toString()) else 0,
                        addressId = addressData!!.id,
                        priceId = priceData!!.id
                        )
            }else{
                priceData!!.value = if(editPrice.text != null) Integer.parseInt( editPrice.text.toString()) else 0
                priceData!!.isDollar = switchPriceType.isChecked
                addressData!!.address = if(editAddress.text != null) editAddress.text.toString() else ""
                addressData!!.apartment = if(editApartement.text != null) editApartement.text.toString() else ""
                addressData!!.country = if(editCountry.text != null) editCountry.text.toString() else ""
                addressData!!.city = if(editCity.text != null) editCity.text.toString() else ""
                realEstateData!!.type = if(editType.text != null) editType.text.toString() else ""
                realEstateData!!.description = if(editDescription.text != null) editDescription.text.toString() else ""
                realEstateData!!.surface = if(editSurface.text != null) Integer.parseInt(editSurface.text.toString()) else 0
            }


            val saveTask = SaveEditTask(priceData as Price,realEstateData as RealEstate, addressData as Address,
                    this, mExisting)
            saveTask.execute()
        }
    }

    class SaveEditTask(private val pPrice: Price,private val pRealEstate: RealEstate,private val pAddress: Address,
                       private val pContext: Context, private val pExisting : Boolean)
        : AsyncTask<Void,Void,Boolean>(){
        override fun onPostExecute(result: Boolean) {
            if(result){
                val intent = Intent(pContext, MainActivity::class.java)
                pContext.startActivity(intent)
            }else{
                Toast.makeText(pContext,"Save Failed",Toast.LENGTH_LONG).show()
            }
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Void?): Boolean {
            val db = Room.databaseBuilder(pContext,AppDatabase::class.java,"database").build()
            try{
                if(pExisting)
                    db.realEstateDao().updateRealEstate(pRealEstate,pPrice,pAddress)
                else
                    db.realEstateDao().insertNewRealEstate(pRealEstate,pPrice,pAddress)
                return true
            }
            catch (e : Exception){
                println(e.message)
                return false
            }
        }
    }
}