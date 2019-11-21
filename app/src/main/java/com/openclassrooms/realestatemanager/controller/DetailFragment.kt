package com.openclassrooms.realestatemanager.controller


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Entity.*
import com.openclassrooms.realestatemanager.model.FragmentMediaAdapter
import java.net.HttpURLConnection
import java.net.URL


/**
 * Fragment to show Detail of a RealEstate
 */
class DetailFragment : Fragment(), AsyncImageOutput {

    private var realEstateData : RealEstate? = null
    private var priceData : Price? = null
    private var addressData : Address? = null
    private var agentData : Agent? = null
    private var mView : View? = null


    override fun imageFinish(imagesOutput: ArrayList<Image>) {


        val mAdapter = FragmentMediaAdapter(imagesOutput,this.context!!)
        val recyclerView = mView!!.findViewById(R.id.fragment_media) as RecyclerView
        val llm = LinearLayoutManager(this.context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager =(llm)
        recyclerView.adapter =mAdapter
    }

    override fun interestFinish(interestOutput: ArrayList<Interess>) {
        val interestText = mView!!.findViewById<TextView>(R.id.fragment_Interest_text)
        interestText.text = if(interestOutput.size == 0) "nothing" else ""
        interestText.text = "${interestText.text}"

        for(interest in interestOutput){
            interestText.text = if(interestText.text == "") interest.name else StringBuilder( "${interestText.text} \n ${interest.name}")
        }

    }

    override fun mapFinish(mapOutput: Bitmap) {
        if(mView != null) {
            val mapImage = mView!!.findViewById(R.id.fragment_map) as ImageView
            mapImage.setImageBitmap(mapOutput)
        }
    }

    companion object{
        fun newInstance(pRealEstate: RealEstate?, pPrice: Price, pAddress: Address,pAgent: Agent) = DetailFragment().apply{
            realEstateData = pRealEstate
            priceData = pPrice
            addressData = pAddress
            agentData = pAgent
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

        // AIzaSyACRApsQtNqJSIupQcUST_jmv-5TbDTBQM


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
        val agentText = pView.findViewById(R.id.fragment_agent_text) as TextView

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

            agentText.text = agentData!!.name

            val interestTask = InterestTask(Room.databaseBuilder(this.context!!, AppDatabase::class.java, "database").build())
            interestTask.delegate = this
            interestTask.execute(realEstateData!!.Interess)


            val url = URL("https://maps.googleapis.com/maps/api/staticmap" +
                    "?center="+ "${addressData!!.address},${addressData!!.city},${addressData!!.country}" +
                    ",CA&zoom=16&size=400x400&key=AIzaSyACRApsQtNqJSIupQcUST_jmv-5TbDTBQM")
            val mapTask = MapAsyncTask()
            mapTask.delegate = this
            mapTask.execute(url)

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
            intent.putExtra("SELECTION_AGENT",agentData)
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

                /*val bitmapArray = ArrayList<Bitmap>()
                for(image in imageData) {
                    val bitmapObject = MediaStore.Images.Media.getBitmap(pContext.contentResolver, Uri.parse(image.Uri))
                    bitmapArray.add(bitmapObject)
                }*/

                return pDatabase.realEstateDao().getImageOfEstate(p0[0] as Int) as ArrayList<Image>
            } else {
                return null
            }
        }
    }

    class InterestTask(private val pDatabase: AppDatabase) :AsyncTask<String,Void,ArrayList<Interess>>(){
        var delegate : AsyncImageOutput? = null
        override fun onPostExecute(result: ArrayList<Interess>) {
            delegate!!.interestFinish(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: String?): ArrayList<Interess> {
            val result : ArrayList<Interess> = ArrayList()
            val listId = p0[0]!!.reader().readLines()
            for(idString in listId){
               try{
                   val idInt = idString.toInt()
                   val retrieve: Interess = pDatabase.realEstateDao().getInteressByID(idInt)
                   result.add(retrieve)
               }catch (e : NumberFormatException){
                    e.printStackTrace()
               }
            }
            return result
        }
    }

    class MapAsyncTask : AsyncTask<URL,Void,Bitmap>(){
        var delegate : AsyncImageOutput? = null

        override fun onPostExecute(result: Bitmap?) {
            if(result != null) delegate!!.mapFinish(result)
            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: URL): Bitmap? {
            var result : Bitmap? = null
                try{
                    val connection = p0[0].openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()

                    val input = connection.inputStream
                    result = BitmapFactory.decodeStream(input)
                }catch (e : Exception){
                    e.printStackTrace()
                }
            return result
            }


    }
}

interface AsyncImageOutput{
    fun imageFinish(imagesOutput : ArrayList<Image>)
    fun interestFinish(interestOutput : ArrayList<Interess>)
    fun mapFinish(mapOutput : Bitmap)
}