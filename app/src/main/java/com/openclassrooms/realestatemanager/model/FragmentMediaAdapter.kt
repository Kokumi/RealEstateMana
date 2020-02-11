package com.openclassrooms.realestatemanager.model

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.model.Entity.Image
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.MediaImageActivity


/**
 * Created by Debruyckère Florian on 30/09/2019.
 */
class FragmentMediaAdapter(private val pData: ArrayList<Image>,private val pContext: Context) :  RecyclerView.Adapter<FragmentMediaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(
        //                Uri.parse("content://com.openclassrooms.realestatemanager.provider/"+ Image.class.getSimpleName()), USER_ID),
        //                null,null,null,null);


        holder.display(pData[position].id,pContext)
    }

    class ViewHolder(cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.media_cell_image)

        /**
         * display images
         */
        fun display(pImage: Int, pContext : Context){
            val contentResolver = pContext.contentResolver
            val cursor : Cursor = contentResolver.query(ContentUris.withAppendedId(
                    Uri.parse("content://com.openclassrooms.realestatemanager.provider/+ ${Image::class.simpleName}"),pImage.toLong()),
            null,null,null,null)!!
            val imageUrl = Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow("uri")))

            println("image url: $imageUrl")


            imageView.setImageBitmap(BitmapFactory.decodeFile(imageUrl.path))

            imageView.setOnClickListener{
                val intent = Intent(pContext,MediaImageActivity::class.java)
                intent.putExtra("IMAGE",imageUrl.path)
                pContext.startActivity(intent)}

            //<-android:name=".RealEstateManager"->
            //multiDexEnable true
        }
    }
}