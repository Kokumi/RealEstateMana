package com.openclassrooms.realestatemanager.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

class MortgageActivity : AppCompatActivity() {

    private var moneyUnit = "$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mortgage)


        val mortgageEdit = findViewById<EditText>(R.id.mg_mortgage_value)
        val interestEdit = findViewById<EditText>(R.id.mg_interest_value)
        val contributionEdit = findViewById<EditText>(R.id.mg_contribution_value)
        val durationEdit = findViewById<EditText>(R.id.mg_duration_value)
        val validButton = findViewById<Button>(R.id.mg_button)
        val moneySwitch = findViewById<Switch>(R.id.mg_money_unit)


        moneySwitch.setOnClickListener{
            moneySwitch.text = if(moneySwitch.isChecked) "€" else "$"
            moneyUnit = if(moneySwitch.isChecked) "€" else "$"
        }
        // launch mortgage calculation
        validButton.setOnClickListener {
            val percentInteress = "1.${interestEdit.text}"
            calculation(if(mortgageEdit.text.toString() != "") mortgageEdit.text.toString().toInt() else 0,
                    if(interestEdit.text.toString() != "") percentInteress.toDouble() else 0.0,
                    if(contributionEdit.text.toString() != "") contributionEdit.text.toString().toInt() else 0,
                    if(durationEdit.text.toString() != "") durationEdit.text.toString().toInt() else 0)
        }
    }

    /**
     * to calculation the mortgage
     */
    private fun calculation(pMortgage : Int, pInterest : Double, pContribution: Int, pDuration : Int){
        val resultText = findViewById<TextView>(R.id.mg_result)
        val format = DecimalFormat("#.##")
        format.roundingMode = RoundingMode.CEILING

        val result = ((pMortgage-pContribution) * (pInterest / 12)) / (1 - Math.pow(1 + (pInterest/12),-pDuration.toDouble()))

        resultText.text = StringBuilder(getString(R.string.mg_result)+" ${format.format(result)} $moneyUnit")
    }
}