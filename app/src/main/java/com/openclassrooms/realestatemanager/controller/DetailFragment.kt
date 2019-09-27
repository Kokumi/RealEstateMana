package com.openclassrooms.realestatemanager.controller


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.RealEstate
import java.lang.StringBuilder

/**
 * Fragment to show Detail of a RealEstate
 */
class DetailFragment : Fragment() {

    private val extraRealEstate = "RealEstate"
    //val realEstateData: RealEstate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        // Inflate the layout for this fragment


        val intent : Intent? = activity?.intent
        val realEstateData =if(intent != null) intent.extras?.getSerializable(extraRealEstate) as? RealEstate else null
        display(realEstateData,view)

        //return inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    private fun display(pRealEstate: RealEstate?, pView: View){
        val warningLayout = pView.findViewById(R.id.fragment_warning_layout) as FrameLayout
        val descriptionText = pView.findViewById(R.id.fragment_description_text) as TextView
        val locationText = pView.findViewById(R.id.fragment_location_text) as TextView
        val roomText = pView.findViewById(R.id.fragment_room_text) as TextView
        val surfaceText = pView.findViewById(R.id.fragment_surface_text) as TextView



        if(pRealEstate != null) {
            warningLayout.visibility = View.GONE
            descriptionText.text = pRealEstate.description
            locationText.text = StringBuilder("${pRealEstate.address.address}\n" +
                    "${pRealEstate.address.appartement}\n" +
                    "${pRealEstate.address.city}\n$" +
                    {pRealEstate.address.country})
            roomText.text = pRealEstate.room.toString()
            surfaceText.text = pRealEstate.surface.toString()
        }else{
            warningLayout.visibility = View.VISIBLE
        }

    }
}
