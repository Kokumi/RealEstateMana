package com.openclassrooms.realestatemanager.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Entity.Image

/**
 * Created by Debruyckère Florian on 30/09/2019.
 */
class FragmentMediaAdapter(private val pData: ArrayList<Image>, private val pContext : Context) :  RecyclerView.Adapter<FragmentMediaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // val image = MediaStore.Images.Media.getBitmap(pContext.contentResolver,Uri.parse(pData[position]))
        //val image = MediaStore.Images.Media.getBitmap(pContext.contentResolver,Uri.parse(pData[position].Uri))
        holder.display(Uri.parse(pData[position].Uri),pContext)
    }

    class ViewHolder(cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.media_cell_image)

        fun display(pImage: Uri, pContext: Context){
            if(ActivityCompat.checkSelfPermission(pContext, Manifest.permission.MANAGE_DOCUMENTS) == PackageManager.PERMISSION_GRANTED)
            imageView.setImageURI(pImage)
            else{
                val perm = arrayOf(Manifest.permission.MANAGE_DOCUMENTS)
                requestPermissions(pContext as Activity,perm,0)
                if(ActivityCompat.checkSelfPermission(pContext, Manifest.permission.MANAGE_DOCUMENTS) == PackageManager.PERMISSION_GRANTED)
                    imageView.setImageURI(pImage)

            }

        }
    }
}