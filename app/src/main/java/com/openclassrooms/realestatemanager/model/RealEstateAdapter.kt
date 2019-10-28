package com.openclassrooms.realestatemanager.model

//import android.support.v7.widget.RecyclerView
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
import java.lang.StringBuilder

/**
 * Created by Debruyckère Florian on 20/09/2019.
 */

class RealEstateAdapter(private val pData : List<RealEstate>,
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

        private var mPrice :Price? = null
        private var mAddress : Address? = null

        fun display(pRealEstate: RealEstate, pActivity: AppCompatActivity){
            typeView.text = pRealEstate.type

            val database = Room.databaseBuilder(pActivity, AppDatabase::class.java, "database").build()
            val price = PriceTask(this,database)
            price.execute(pRealEstate.priceId)
            val address = AddressTask(this, database)
            address.execute(pRealEstate.addressId)

            cellView.setOnClickListener {

                val dF = DetailFragment.newInstance(pRealEstate, mPrice!!, mAddress!!)
                pActivity.supportFragmentManager.beginTransaction().replace(R.id.main_detail_fragment, dF).commit()
            }
        }

        fun priceDisplay(pPrice : Price?){
            priceView.text = if(pPrice != null) StringBuilder( pPrice.value.toString() + " "
            + if(pPrice.isDollar) "$" else "€") else "not Found"
            mPrice = pPrice
        }

        fun addressDisplay(pAddress: Address){
            cityView.text = pAddress.city
            mAddress = pAddress
        }
    }

    class PriceTask(private val pHolder : ViewHolder, private val pDatabase : AppDatabase) : AsyncTask<Int,Void,Price>(){


        override fun onPostExecute(result: Price?) {
            pHolder.priceDisplay(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: Int?): Price? {

            return if(p0.get(0)!= null) pDatabase.realEstateDao().getPriceById(p0.get(0) as Int)
                else null
        }
    }

    class AddressTask(private val pHolder: ViewHolder, private val pDatabase: AppDatabase) : AsyncTask<Int,Void,Address>(){
        override fun onPostExecute(result: Address) {
            pHolder.addressDisplay(result)
            super.onPostExecute(result)
            this.cancel(true)
        }

        override fun doInBackground(vararg p0: Int?): Address {
            return if(p0.get(0) != null) pDatabase.realEstateDao().getAddressById(p0.get(0) as Int)
                else Address(0,"","","NotFound","")
        }
    }
}