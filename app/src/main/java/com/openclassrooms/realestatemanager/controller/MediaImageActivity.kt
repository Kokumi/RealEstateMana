package com.openclassrooms.realestatemanager.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import java.lang.Exception

class MediaImageActivity : AppCompatActivity() {

    private var mBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_image)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        /*val actionBar = actionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)*/

        // get selected image from intent
        mBitmap = if(intent.extras?.get("IMAGE") != null) BitmapFactory.decodeFile(intent.extras?.get("IMAGE") as String)
        else null


        val photoImage = findViewById<ImageView>(R.id.medimage_image)
        try {
            photoImage.setImageBitmap(mBitmap)
        }catch (e : Exception){
            Toast.makeText(this, "Wrong image",Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
