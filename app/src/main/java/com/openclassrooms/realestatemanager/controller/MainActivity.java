package com.openclassrooms.realestatemanager.controller;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.Address;
import com.openclassrooms.realestatemanager.model.AppDatabase;
import com.openclassrooms.realestatemanager.model.Price;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RealEstateAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataTask task = new GetDataTask();
        task.execute();
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
        this.textViewQuantity.setText(String.valueOf(quantity)); testa mdp: 17
    }*/

    private void configureAdapter(List<RealEstate> pData){

        mAdapter = new RealEstateAdapter(pData,this);
        RecyclerView recyclerView = findViewById(R.id.main_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
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

        MenuItem search = menu.findItem(R.id.tool_search);
        SearchView searchView = (SearchView)search.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.tool_search){
            Toast.makeText(this, "Search Menu", Toast.LENGTH_SHORT).show();
            ArrayList<RealEstate> result = new ArrayList<>();

            //for(RealEstate current : mRealEstates){}
        }

        return super.onOptionsItemSelected(item);
    }

    class GetDataTask extends AsyncTask<Context, Void, List<RealEstate>>{
        @Override
        protected void onPostExecute(List<RealEstate> realEstates) {
            configureAdapter(realEstates);
            super.onPostExecute(realEstates);
        }

        @Override
        protected List<RealEstate> doInBackground(Context... contexts) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database").build();

            return db.realEstateDao().getAll();
        }
    }
}
