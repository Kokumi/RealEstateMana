package com.openclassrooms.realestatemanager.model

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
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
        holder.display(Uri.parse(pData[position].Uri),pContext)
    }

    class ViewHolder(cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.media_cell_image)

        fun display(pImage: Uri, pContext : Context){
            println("image url: ${pImage.path}")
            val bitmap = BitmapFactory.decodeFile(pImage.path)
            imageView.setImageBitmap(BitmapFactory.decodeFile(pImage.path))
            //imageView.setImageBitmap(bitmap)

            imageView.setOnClickListener{
                val intent = Intent(pContext,MediaImageActivity::class.java)
                intent.putExtra("IMAGE",pImage.path)
                pContext.startActivity(intent)
            }
        }
    }
}