package com.openclassrooms.realestatemanager.model

//import android.support.v7.widget.RecyclerView
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.DetailFragment

/**
 * Created by Debruyckère Florian on 20/09/2019.
 */

class RealEstateAdapter(private val pData : ArrayList<RealEstate>,
                        private val pActivity: AppCompatActivity)
    :  RecyclerView.Adapter<RealEstateAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.real_estate_cell, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount() = pData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(pData[position],pActivity)
    }

    class ViewHolder(private val cellView: View) : RecyclerView.ViewHolder(cellView){

        private val imageView : ImageView = cellView.findViewById(R.id.cell_image)
        private val typeView : TextView = cellView.findViewById(R.id.cell_type)
        private val priceView : TextView = cellView.findViewById(R.id.cell_price)
        private val cityView : TextView = cellView.findViewById(R.id.cell_city)
        private val extraRealEstate = "RealEstate"

        fun display(pRealEstate: RealEstate, pActivity: AppCompatActivity){
            typeView.text = pRealEstate.type
            priceView.text = "${pRealEstate.price}"
            cityView.text = pRealEstate.address.city

            cellView.setOnClickListener {
                val intent = Intent(pActivity.applicationContext, DetailFragment::class.java)
                /*intent.putExtra(extraRealEstate,pRealEstate)
                pContext.startActivity(intent)*/

                val dF = DetailFragment.newInstance(pRealEstate)
                val detailFragment = Fragment.instantiate(pActivity.applicationContext,DetailFragment::class.java.name) as DetailFragment
                //pActivity.supportFragmentManager.beginTransaction().replace(R.id.main_detail_fragment, detailFragment).commit()
                pActivity.supportFragmentManager.beginTransaction().replace(R.id.main_detail_fragment, dF).commit()
            }
        }
    }
}