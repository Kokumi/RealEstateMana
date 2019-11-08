package com.openclassrooms.realestatemanager.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.room.Room
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.AppDatabase
import com.openclassrooms.realestatemanager.model.Entity.*
import kotlinx.android.synthetic.main.activity_edit.*
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditActivity : AppCompatActivity(), ImageTaskRecepter {
    private var realEstateData : RealEstate? = null
    private var priceData : Price? = null
    private var addressData : Address? = null
    private var agentData : Agent? = null
    private var mExisting = false
    private val gallery= 1

    private var nbImageText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        realEstateData = if(intent.extras?.get("SELECTION") != null) intent.extras?.get("SELECTION") as RealEstate
        else null
        priceData = if(intent.extras?.get("SELECTION_PRICE") != null) intent.extras?.get("SELECTION_PRICE") as Price
        else null
        addressData = if(intent.extras?.get("SELECTION_ADDRESS") != null) intent.extras?.get("SELECTION_ADDRESS") as Address
        else null
        agentData = if(intent.extras?.get("SELECTION_AGENT") != null) intent.extras?.get("SELECTION_AGENT") as Agent
        else null

        if(realEstateData!= null && priceData!=null && addressData!=null) mExisting = true


        display()
    }

    override fun imageRecepter(pImageData: ArrayList<Image>) {
        nbImageText!!.text = StringBuilder(getString(R.string.edit_number_image) + pImageData.size)
    }

    private fun display(){
        val rED = realEstateData
        val pD = priceData
        val aD = addressData
        val agD = agentData

        val editType = findViewById<EditText>(R.id.edit_type)
        val editPrice = findViewById<EditText>(R.id.edit_price)
        val switchPriceType = findViewById<Switch>(R.id.edit_price_type)
        val editAddress = findViewById<EditText>(R.id.edit_address)
        val editCity = findViewById<EditText>(R.id.edit_city)
        val editCountry = findViewById<EditText>(R.id.edit_country)
        val editApartment = findViewById<EditText>(R.id.edit_apartment)
        val editAgent = findViewById<EditText>(R.id.edit_agent)
        val editDescription = findViewById<EditText>(R.id.edit_description)
        val editSurface = findViewById<EditText>(R.id.edit_surface)
        val editStatus = findViewById<Spinner>(R.id.edit_status)
        val finishButton = findViewById<Button>(R.id.edit_validation)
        val imageButton  = findViewById<Button>(R.id.edit_image)
        nbImageText = findViewById(R.id.edit_image_number)

        spinnerConfiguration(editStatus)

        if(realEstateData != null){

            editPrice.setText(pD!!.value.toString())
            switchPriceType.isChecked = pD.isDollar

            editAgent.setText(agD!!.name)

            editDescription.setText(rED!!.description)
            editSurface.setText(rED.surface.toString())
            editType.setText(rED.type)

            editAddress.setText(aD!!.address)
            editCity.setText(aD.city)
            editCountry.setText(aD.country)
            editApartment.setText(aD.apartment)

            val imageTask = ImageTask(Room.databaseBuilder(this,AppDatabase::class.java,"database").build())
            imageTask.delegate = this
            imageTask.execute(realEstateData!!.id)
        } else{
            edit_image_number.text = StringBuilder(R.string.edit_number_image.toString() + "0")
        }


        imageButton.setOnClickListener{
            val imageIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(imageIntent,gallery)
        }


        finishButton.setOnClickListener{

            val todayDay = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(Date())
            if(priceData == null && realEstateData == null && addressData == null && agentData == null){
                priceData = Price(value = if (editPrice.text != null) Integer.parseInt(editPrice.text.toString()) else 0,
                        isDollar = switchPriceType.isChecked)


                addressData = Address(address = if (editAddress.text != null) editAddress.text.toString() else "",
                        apartment = if (editApartment.text != null) editApartment.text.toString() else "",
                        city = if (editCity.text != null) editCity.text.toString() else "",
                        country = if (editCountry.text != null) editCountry.text.toString() else "")


                realEstateData = RealEstate(type = if (editType.text != null) editType.text.toString() else "",
                        description = if (editDescription.text != null) editDescription.text.toString() else "",
                        surface = if (editSurface.text != null) Integer.parseInt(editSurface.text.toString()) else 0,
                        statut = editStatus.selectedItem.toString(),
                        addressId = addressData!!.id,
                        priceId = priceData!!.id,
                        dateEntree = todayDay,
                        dateVente = if (editStatus.selectedItemPosition == 2) todayDay else "not sold")

                agentData = Agent(name= if(editAgent.text != null) editAgent.text.toString() else "")
            }else{
                priceData!!.value = if(editPrice.text != null) Integer.parseInt( editPrice.text.toString()) else 0
                priceData!!.isDollar = switchPriceType.isChecked
                addressData!!.address = if(editAddress.text != null) editAddress.text.toString() else ""
                addressData!!.apartment = if(editApartment.text != null) editApartment.text.toString() else ""
                addressData!!.country = if(editCountry.text != null) editCountry.text.toString() else ""
                addressData!!.city = if(editCity.text != null) editCity.text.toString() else ""
                realEstateData!!.type = if(editType.text != null) editType.text.toString() else ""
                realEstateData!!.description = if(editDescription.text != null) editDescription.text.toString() else ""
                realEstateData!!.surface = if(editSurface.text != null) Integer.parseInt(editSurface.text.toString()) else 0
                realEstateData!!.statut = editStatus.selectedItem.toString()
                realEstateData!!.dateVente = if(editStatus.selectedItemPosition == 2)todayDay else "not sold"
                agentData!!.name =if(editAgent.text != null) editAgent.text.toString() else ""
            }

            println(realEstateData!!.statut)
            val saveTask = SaveEditTask(priceData as Price,realEstateData as RealEstate,
                    addressData as Address,agentData as Agent,
                    this, mExisting)
            saveTask.execute()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_CANCELED && requestCode == gallery){
            if(data!=null){
                if(realEstateData!= null) {
                    val contentURI = data.data
                    val task = AddImageTask(this.baseContext)
                    task.execute(Image(realEstateData!!.id, contentURI!!.toString()))
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun spinnerConfiguration(pSpinner: Spinner){
        val typeItem = arrayOf("Not Ready", "On sale", "Sold")
        val dropAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, typeItem)
        pSpinner.adapter =dropAdapter

        if(realEstateData != null) {
            when (realEstateData!!.statut) {
                "Not Ready" -> pSpinner.setSelection(0)

                "On sale" -> pSpinner.setSelection(1)

                "Sold" -> pSpinner.setSelection(2)

                else -> pSpinner.setSelection(0)
            }
        } else{
            pSpinner.setSelection(0)
        }
    }

    class SaveEditTask(private val pPrice: Price, private val pRealEstate: RealEstate,
                       private val pAddress: Address, private val pAgent: Agent,
                       private val pContext : Context, private val pExisting : Boolean)
        : AsyncTask<Void,Void,Boolean>(){
        override fun onPostExecute(result: Boolean) {
            if(result){
                val intent = Intent(pContext, MainActivity::class.java)
                pContext.startActivity(intent)
            }else{
                Toast.makeText(pContext,"Save Failed",Toast.LENGTH_LONG).show()
            }
            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Void?): Boolean {
            val db = Room.databaseBuilder(pContext,AppDatabase::class.java,"database").build()
            try{
                if(pExisting)
                    db.realEstateDao().updateRealEstate(pRealEstate,pPrice,pAddress,pAgent)
                else
                    db.realEstateDao().insertNewRealEstate(pRealEstate,pPrice,pAddress,pAgent)
                return true
            }
            catch (e : Exception){
                println(e.message)
                return false
            }
        }
    }
    class ImageTask(private val pDatabase : AppDatabase) : AsyncTask<Int,Void,ArrayList<Image>>(){
        var delegate : ImageTaskRecepter? = null

        override fun onPostExecute(result: ArrayList<Image>) {
            delegate!!.imageRecepter(result)
            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Int?): ArrayList<Image> {
            return pDatabase.realEstateDao().getImageOfEstate(p0[0]!!) as ArrayList
        }
    }


    class AddImageTask(private val pContext: Context) : AsyncTask<Image,Void,Boolean>(){
        override fun onPostExecute(result: Boolean) {
            Toast.makeText(pContext,if(result) "image saved" else "Error",Toast.LENGTH_SHORT).show()
            this.cancel(true)
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg p0: Image): Boolean {
            val db = Room.databaseBuilder(pContext,AppDatabase::class.java,"database").build()
            try {
                db.realEstateDao().insertImage(p0[0])
                return true
            }catch(e : Exception){
                e.printStackTrace()
                return false
            }
        }
    }

}
interface ImageTaskRecepter{
    fun imageRecepter(pImageData : ArrayList<Image>)
}