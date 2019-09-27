package com.openclassrooms.realestatemanager.model

//import android.support.v7.widget.RecyclerView
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.DetailFragment

/**
 * Created by Debruyckère Florian on 20/09/2019.
 */

class RealEstateAdapter(private val pData : ArrayList<RealEstate>, private val pContext : Context) :  RecyclerView.Adapter<RealEstateAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.real_estate_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(pData[position],pContext)
    }

    class ViewHolder(private val cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.cell_image)
        private val typeView : TextView = cellView.findViewById(R.id.cell_type)
        private val priceView : TextView = cellView.findViewById(R.id.cell_price)
        private val cityView : TextView = cellView.findViewById(R.id.cell_city)
        private val extraRealEstate = "RealEstate"

        fun display(pRealEstate: RealEstate, pContext: Context){
            typeView.text = pRealEstate.type
            priceView.text = "${pRealEstate.price}"
            cityView.text = pRealEstate.address.city

            cellView.setOnClickListener {
                val intent = Intent(pContext, DetailFragment::class.java)
                intent.putExtra(extraRealEstate,pRealEstate)
                pContext.startActivity(intent)
            }
        }
    }
}