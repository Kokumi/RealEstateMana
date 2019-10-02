package com.openclassrooms.realestatemanager.model

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R

/**
 * Created by Debruyckère Florian on 30/09/2019.
 */
class FragmentMediaAdapter(private val pData: ArrayList<Bitmap>, private val pContext : Context) :  RecyclerView.Adapter<FragmentMediaAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(pData[position],pContext)
    }

    class ViewHolder(private val cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.media_cell_image)
        //private val extraRealEstate = "RealEstate"

        fun display(pImage: Bitmap, pContext: Context){
            imageView.setImageBitmap(pImage)

        }
    }
}