package com.openclassrooms.realestatemanager.controller;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.AppDatabase;
import com.openclassrooms.realestatemanager.model.AppDatabase_Impl;
import com.openclassrooms.realestatemanager.model.Interess;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RealEstate> mRealEstates = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAdapter();
        configureFragment();

        //AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"database-name").build();
        //final AppDatabase db = new AppDatabase_Impl();
        final AppDatabase db = Room.inMemoryDatabaseBuilder(getApplicationContext(),AppDatabase.class).build();



        final RealEstate estate = new RealEstate(1,"type",0,0,"Description",
               /* new ArrayList<Interess>(),*/"des","never","not",/*new ArrayList<String>(),*/
                0,0,0);
        mRealEstates.add(estate);

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                db.realEstateDao().insertAll(estate);
                return null;
            }
        };

        task.execute();

        //db.realEstateDao().insertAll(estate);
        AsyncTask task2 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                RealEstate test = db.realEstateDao().getAll().get(0);
                System.out.println("saved: " + test.getDescription());
                return null;
            }
        };
        task2.execute();


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
        RealEstate estate = new RealEstate(1,"type",0,0,"Description",
               /*inte,*/"des","never","not",/*new ArrayList<String>(),*/
                0,0,0);
        /*new Price(5000000,false)
        new Address("There","10a","Citycity","land")
        new Agent(0,"nom","tele")*/

        ArrayList<RealEstate> data = new ArrayList<>();
        //RealEstate[] dataB = {estate};
        data.add(estate);

        RealEstateAdapter adapter = new RealEstateAdapter(mRealEstates,this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);

        /*MenuItem searchItem = menu.findItem(R.id.tool_search);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.tool_search){
            Toast.makeText(this, "Search Menu", Toast.LENGTH_SHORT).show();
            ArrayList<RealEstate> result = new ArrayList<>();

            for(RealEstate current : mRealEstates){

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
