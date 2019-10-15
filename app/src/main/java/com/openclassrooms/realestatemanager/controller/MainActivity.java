package com.openclassrooms.realestatemanager.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.Agent;
import com.openclassrooms.realestatemanager.model.AppDatabase;
import com.openclassrooms.realestatemanager.model.Interess;
import com.openclassrooms.realestatemanager.model.Price;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAdapter();
        configureFragment();

        /*AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"database-name").build();
        RealEstate estate = new RealEstate(1,"type",new Price(5000000,false),0,0,"des",
                new Address("There","10a","Citycity","land"),new ArrayList<Interess>(),"not","0",
                "0",new Agent(0,"nom","tele"),new ArrayList<String>());
        //AppDatabase db =

        db.realEstateDao().insertAll(estate);
        RealEstate test = db.realEstateDao().getAll().get(0);

        System.out.println("saved: " + test.getDescription());*/
    }

    /*private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100,false);
        this.textViewQuantity.setTextSize(20);
        //this.textViewQuantity.setText(quantity);  ORIGINAL
        this.textViewQuantity.setText(String.valueOf(quantity)); testa mdp: 17
    }*/

    private void configureAdapter(){
        ArrayList<Interess> inte = new ArrayList<>();
        RealEstate estate = new RealEstate(1,"type",new Price(5000000,false),0,0,"des",
                new Address("There","10a","Citycity","land"),inte,"not","0",
                "0",new Agent(0,"nom","tele"),new ArrayList<String>());

        ArrayList<RealEstate> data = new ArrayList<>();
        //RealEstate[] dataB = {estate};

        data.add(estate);
        RealEstateAdapter adapter = new RealEstateAdapter(data,this);
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
