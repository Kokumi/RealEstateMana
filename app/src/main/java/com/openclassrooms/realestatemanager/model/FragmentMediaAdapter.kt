package com.openclassrooms.realestatemanager.model

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Entity.Image
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 30/09/2019.
 */
class FragmentMediaAdapter(private val pData: ArrayList<Image>) :  RecyclerView.Adapter<FragmentMediaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(Uri.parse(pData[position].Uri))
    }

    class ViewHolder(cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.media_cell_image)

        fun display(pImage: Uri){
            println("image url: ${pImage.path}")
            imageView.setImageBitmap(BitmapFactory.decodeFile(pImage.path))


        }
    }
}