package com.openclassrooms.realestatemanager.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate

class EditActivity(private var realEstateData : RealEstate? = null) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realEstateData = if(intent.extras?.get("SELECTION") != null) intent.extras?.get("SELECTION") as RealEstate
        else null

        display()
    }

    private fun display(){
        val rED = realEstateData

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
            /*editPrice.setText(rED?.price!!.value.toString())
            editAddress.setText(rED.address.address)
            editCity.setText(rED.address.city)
            editCountry.setText(rED.address.country)
            editApartement.setText(rED.address.appartement)
            //editPostal.setText(rED.address.postal)
            editDescription.setText(rED.description)
            editSurface.setText(rED.surface.toString())
            switchPriceType.isChecked = rED.price.isDollar*/
        }

        finishButton.setOnClickListener{
            //Save


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
