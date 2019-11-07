package com.openclassrooms.realestatemanager.controller


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Entity.Address
import com.openclassrooms.realestatemanager.model.Entity.Image
import com.openclassrooms.realestatemanager.model.Entity.Price
import com.openclassrooms.realestatemanager.model.Entity.RealEstate
import com.openclassrooms.realestatemanager.model.FragmentMediaAdapter
import java.lang.StringBuilder

/**
 * Fragment to show Detail of a RealEstate
 */
class DetailFragment : Fragment(), AsyncImageOutput {

    private var realEstateData : RealEstate? = null
    private var priceData : Price? = null
    private var addressData : Address? = null
    private var mView : View? = null


    override fun imageFinish(imagesOutput: ArrayList<Image>) {


        val mAdapter = FragmentMediaAdapter(imagesOutput,this.context!!)
        val recyclerView = mView!!.findViewById(R.id.fragment_media) as RecyclerView
        recyclerView.layoutManager =LinearLayoutManager(this.context)
        recyclerView.adapter =mAdapter
    }

    companion object{
        fun newInstance(pRealEstate: RealEstate?, pPrice: Price, pAddress: Address) = DetailFragment().apply{
            realEstateData = pRealEstate
            priceData = pPrice
            addressData = pAddress
        }
    }

    //private val extraRealEstate = "RealEstate"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        mView = view
        // Inflate the layout for this fragment
        display(view)
        buttonConfig(view)

        if(realEstateData != null) {
            val imageTask = ImageTask(Room.databaseBuilder(this.context!!, AppDatabase::class.java, "database").build(),activity!!.baseContext)
            imageTask.delegate = this
            imageTask.execute(realEstateData!!.id)
        }
        return view
    }

    private fun display( pView: View){
        val warningLayout = pView.findViewById(R.id.fragment_warning_layout) as FrameLayout
        val descriptionText = pView.findViewById(R.id.fragment_description_text) as TextView
        val locationText = pView.findViewById(R.id.fragment_location_text) as TextView
        val roomText = pView.findViewById(R.id.fragment_room_text) as TextView
        val surfaceText = pView.findViewById(R.id.fragment_surface_text) as TextView
        val priceText = pView.findViewById(R.id.fragment_price_text) as TextView
        val statusText = pView.findViewById(R.id.fragment_status_text) as TextView
        val dateEntreeText = pView.findViewById(R.id.fragment_date_entree_text)as TextView
        val dateVenteText = pView.findViewById(R.id.fragment_date_vente_text) as TextView
        //val agentText = pView.findViewById(R.id.fragment_agent_text) as TextView
        val recyclerView = pView.findViewById(R.id.fragment_media) as RecyclerView

        if(realEstateData != null){
            warningLayout.visibility = View.GONE
            descriptionText.text = realEstateData!!.description

            locationText.text = StringBuilder("${addressData!!.address}\n" +
                    "${addressData!!.apartment}\n" +
                    "${addressData!!.city}\n" +
                    addressData!!.country)

            roomText.text = realEstateData!!.room.toString()
            surfaceText.text = realEstateData!!.surface.toString()
            statusText.text = realEstateData!!.statut
            priceText.text = if(priceData!!.isDollar) priceData!!.value.toString() + "$" else priceData!!.value.toString() + "€"

            dateEntreeText.text = realEstateData!!.dateEntree
            dateVenteText.text = realEstateData!!.dateVente

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
        val createButton = pView.findViewById(R.id.fragment_edit_new) as Button

        editButton.setOnClickListener{
            val intent = Intent(context,EditActivity::class.java)
            intent.putExtra("SELECTION",realEstateData)
            intent.putExtra("SELECTION_PRICE",priceData)
            intent.putExtra("SELECTION_ADDRESS",addressData)
            startActivity(intent)
        }
        createButton.setOnClickListener{
            val intent = Intent(context, EditActivity::class.java)
            startActivity(intent)
        }
    }

class ImageTask(private val pDatabase: AppDatabase,private val pContext: Context) : AsyncTask<Int, Void, ArrayList<Image>>(){
        var delegate : AsyncImageOutput? = null
        override fun onPostExecute(result: ArrayList<Image>?) {
            if(result != null) delegate!!.imageFinish(result)


            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Int?): ArrayList<Image>? {
            if(p0[0] != null){
                val imageData = pDatabase.realEstateDao().getImageOfEstate(p0[0] as Int) as ArrayList<Image>

                /*val bitmapArray = ArrayList<Bitmap>()
                for(image in imageData) {
                    val bitmapObject = MediaStore.Images.Media.getBitmap(pContext.contentResolver, Uri.parse(image.Uri))
                    bitmapArray.add(bitmapObject)
                }*/

                return imageData
            } else {
                return null
            }
        }
    }

}
interface AsyncImageOutput{
    fun imageFinish(imagesOutput : ArrayList<Image>)
}