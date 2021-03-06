package com.openclassrooms.realestatemanager.model

import android.net.Uri
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.controller.DetailFragment
import com.openclassrooms.realestatemanager.model.Entity.*
import java.lang.StringBuilder
import kotlin.collections.ArrayList

/**
 * Created by Debruyckère Florian on 20/09/2019.
 */

class RealEstateAdapter(pData : List<RealEstate>,
                        private val pActivity: AppCompatActivity)
    :  RecyclerView.Adapter<RealEstateAdapter.ViewHolder>(){

    private var mData : List<RealEstate> = pData
    private var mPriceData : ArrayList<Price> = ArrayList()
    private var mAddressData = ArrayList<Address>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.real_estate_cell, parent, false) as View

        return ViewHolder(view,this)
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.display(mData[position],pActivity)
    }



    class ViewHolder(private val cellView: View,private val pParent : RealEstateAdapter) : RecyclerView.ViewHolder(cellView),
        AsyncResponse{

        private val imageView : ImageView = cellView.findViewById(R.id.cell_image)
        private val typeView : TextView = cellView.findViewById(R.id.cell_type)
        private val priceView : TextView = cellView.findViewById(R.id.cell_price)
        private val cityView : TextView = cellView.findViewById(R.id.cell_city)

        private var mPrice : Price? = null
        private var mAddress : Address? = null
        private var mAgent : Agent? = null

        /**
         * get address and/or Price from task
         */
        override fun processFinish(priceOutput: Price?, addressOutput : Address?) {
            if(priceOutput != null){ priceDisplay(priceOutput) ; pParent.mPriceData.add(priceOutput)}

            if(addressOutput != null) {addressDisplay(addressOutput) ; pParent.mAddressData.add(addressOutput)}
        }

        /**
         * get image from task
         */
        override fun imageFinish(imageOutput: Image) {
            imageView.setImageURI(Uri.parse(imageOutput.Uri))
        }

        /**
         * get agent from task
         */
        override fun agentFinish(agentOutput: Agent) {
            mAgent= agentOutput
        }

        /**
         * display all real estates
         */
        fun display(pRealEstate: RealEstate, pActivity: AppCompatActivity){
            typeView.text = pRealEstate.type

            val database = Room.databaseBuilder(pActivity, AppDatabase::class.java, "database").build()
            val price = PriceTask(database)
            price.delegate = this
            price.execute(pRealEstate.priceId)

            val address = AddressTask(database)
            address.delegate = this
            address.execute(pRealEstate.addressId)

            val image = ImageTask(database)
            image.delegate = this
            image.execute(pRealEstate.id)

            val agent = AgentTask(database)
            agent.delegate = this
            agent.execute(pRealEstate.id)


            cellView.setOnClickListener {

                val dF = DetailFragment.newInstance(pRealEstate, mPrice!!, mAddress!!,mAgent!!)
                pActivity.supportFragmentManager.beginTransaction().replace(R.id.main_detail_fragment, dF).commit()
            }
        }

        /**
         * display real estate's price
         */
        private fun priceDisplay(pPrice : Price?){
            priceView.text = if(pPrice != null) StringBuilder( pPrice.value.toString() + " "
            + if(pPrice.isDollar) "$" else "€") else "not Found"
            mPrice = pPrice
        }

        /**
         * display real estate's address
         */
        private fun addressDisplay(pAddress: Address){
            cityView.text = pAddress.city
            mAddress = pAddress
        }
    }

    /**
     * task to get real estate's price
     */
    class PriceTask( private val pDatabase : AppDatabase) : AsyncTask<Int,Void, Price>(){
        var delegate : AsyncResponse? = null

        override fun onPostExecute(result: Price?) {
            delegate!!.processFinish(priceOutput = result)
            //pHolder.priceDisplay(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: Int?): Price? {

            return if(p0[0]!= null) pDatabase.realEstateDao().getPriceById(p0[0] as Int)
                else null
        }
    }

    /**
     * task to get real estate's address
     */
    class AddressTask( private val pDatabase: AppDatabase) : AsyncTask<Int,Void, Address>(){
        var delegate : AsyncResponse? = null

        override fun onPostExecute(result: Address) {
            delegate!!.processFinish(addressOutput = result)
            //pHolder.addressDisplay(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: Int?): Address {
            return if(p0[0] != null) pDatabase.realEstateDao().getAddressById(p0[0] as Int)
                else Address(0, "", "", "NotFound", "")
        }
    }

    /**
     * task to get real estate's image
     */
    class ImageTask(private val pDatabase: AppDatabase) : AsyncTask<Int,Void, Image>(){
        var delegate : AsyncResponse? = null
        override fun onPostExecute(result: Image?) {
            if(result != null) delegate!!.imageFinish(result)

            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Int?): Image? {
            if(p0[0] != null){
                val imageData = pDatabase.realEstateDao().getImageOfEstate(p0[0] as Int)
                return if(imageData.isNotEmpty()) imageData[0] else null
            } else {
                return null
            }
        }
    }

    /**
     * task to get real estate's agent
     */
    class AgentTask(private val pDatabase: AppDatabase) : AsyncTask<Int,Void, Agent>(){
        var delegate : AsyncResponse? = null
        override fun onPostExecute(result: Agent?) {
            if(result != null) delegate!!.agentFinish(result)

            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Int?): Agent? {
            if(p0[0] != null){
                return pDatabase.realEstateDao().getAgent(p0[0] as Int)
            } else {
                return null
            }
        }
    }

}interface AsyncResponse{
    fun processFinish(priceOutput: Price? = null, addressOutput : Address? = null)
    fun imageFinish(imageOutput : Image)
    fun agentFinish(agentOutput : Agent)
}