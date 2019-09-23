package com.openclassrooms.realestatemanager.controller;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Agent;
import com.openclassrooms.realestatemanager.model.Interess;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateAdapter;
import com.openclassrooms.realestatemanager.model.Statut;
import com.openclassrooms.realestatemanager.model.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAdapter();
        configureFragment();
    }

    /*private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100,false);
        this.textViewQuantity.setTextSize(20);
        //this.textViewQuantity.setText(quantity);  ORIGINAL
        this.textViewQuantity.setText(String.valueOf(quantity));
    }*/

    private void configureAdapter(){
        ArrayList<Interess> inte = new ArrayList<>();
        RealEstate estate = new RealEstate(1,"type",0,0,0,"des",
                "address","city",inte,"not","0",
                "0",new Agent(0,"nom","tele"));

        ArrayList<RealEstate> data = new ArrayList<>();
        RealEstate[] dataB = {estate};

        data.add(estate);
        RealEstateAdapter adapter = new RealEstateAdapter(dataB);
        RecyclerView recyclerView = findViewById(R.id.main_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void configureFragment(){
        FragmentTransaction fT = getSupportFragmentManager().beginTransaction();
        DetailFragment fragment = new DetailFragment();
        fT.add(R.id.main_detail_fragment,fragment);
        fT.commit();
    }
}
