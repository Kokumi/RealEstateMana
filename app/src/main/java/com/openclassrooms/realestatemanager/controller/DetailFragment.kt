package com.openclassrooms.realestatemanager.controller


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Fragment to show Detail of a RealEstate
 */
class DetailFragment : Fragment() {

    private val extraRealEstate = "RealEstate"

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment

        val intent : Intent? = activity?.intent
        val realEstateData =if(intent != null) intent.extras?.getSerializable(extraRealEstate) as RealEstate else null

        if(realEstateData != null) display(realEstateData)

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun display(pRealEstate: RealEstate){
        fragment_description_text.text = pRealEstate.description
        fragment_location_text.text = pRealEstate.address
        fragment_room_text.text = pRealEstate.room.toString()
        fragment_surface_text.text = pRealEstate.surface.toString()

    }

}
