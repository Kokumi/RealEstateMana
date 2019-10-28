package com.openclassrooms.realestatemanager.controller


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.FragmentMediaAdapter
import com.openclassrooms.realestatemanager.model.Price
import com.openclassrooms.realestatemanager.model.RealEstate
import java.lang.StringBuilder

/**
 * Fragment to show Detail of a RealEstate
 */
class DetailFragment : Fragment() {

    private var realEstateData : RealEstate? = null
    private var priceDate : Price? = null
    private var addressData : Address? = null

    companion object{
        fun newInstance(pRealEstate: RealEstate?, pPrice: Price, pAddress: Address) = DetailFragment().apply{
            realEstateData = pRealEstate
            priceDate = pPrice
            addressData = pAddress
        }
    }

    //private val extraRealEstate = "RealEstate"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        // Inflate the layout for this fragment
        display(view)
        buttonConfig(view)


        //return inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    private fun display( pView: View){
        val warningLayout = pView.findViewById(R.id.fragment_warning_layout) as FrameLayout
        val descriptionText = pView.findViewById(R.id.fragment_description_text) as TextView
        val locationText = pView.findViewById(R.id.fragment_location_text) as TextView
        val roomText = pView.findViewById(R.id.fragment_room_text) as TextView
        val surfaceText = pView.findViewById(R.id.fragment_surface_text) as TextView
        val recyclerView = pView.findViewById(R.id.fragment_media) as RecyclerView

        if(realEstateData != null){
            warningLayout.visibility = View.GONE
            descriptionText.text = realEstateData!!.description

            locationText.text = StringBuilder("${addressData!!.address}\n" +
                    "${addressData!!.apartment}\n" +
                    "${addressData!!.city}\n$" +
                    {addressData!!.country})

            roomText.text = realEstateData!!.room.toString()
            surfaceText.text = realEstateData!!.surface.toString()

            val llm = LinearLayoutManager(activity?.applicationContext)
            llm.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager =(llm)
            //recyclerView.adapter = FragmentMediaAdapter(realEstateData!!.imageList, activity!!.applicationContext)
        }else{
            warningLayout.visibility = View.VISIBLE
        }

    }

    private fun buttonConfig(pView: View){
        val editButton = pView.findViewById(R.id.fragment_edit_button) as Button

        editButton.setOnClickListener{
            val intent = Intent(context,EditActivity::class.java)
            intent.putExtra("SELECTION",realEstateData)
            intent.putExtra("SELECTION_PRICE",priceDate)
            intent.putExtra("SELECTION_ADDRESSS",addressData)
            startActivity(intent)
        }
    }
}
