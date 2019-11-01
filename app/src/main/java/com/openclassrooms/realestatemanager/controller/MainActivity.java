package com.openclassrooms.realestatemanager.controller;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity implements SearchAsyncResponse {

    RealEstateAdapter mAdapter;
    ArrayList<RealEstate> mData = new ArrayList<>();


    ArrayList<RealEstate> searchResult;

    @Override
    public void positiveResult(RealEstate pRealEstate) {
        searchResult.add(pRealEstate);
        configureAdapter(searchResult);
    }

    @Override
    public void negativeResult() {
        configureAdapter(searchResult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataTask task = new GetDataTask();
        try {
            mData = task.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.tool_search){
            Toast.makeText(this, "Search Menu", Toast.LENGTH_SHORT).show();
            //ArrayList<RealEstate> result = new ArrayList<>();
            showDialog();

            //for(RealEstate current : mRealEstates){}
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);

        Button dialogButton = dialog.findViewById(R.id.dialog_valid);
        final EditText dialogEdit = dialog.findViewById(R.id.dialog_filter);
        final Spinner dialogType = dialog.findViewById(R.id.dialog_type);

        String[] typeItem = new String[]{"Type","City","Price"};
        ArrayAdapter<String> dropAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, typeItem);
        dialogType.setAdapter(dropAdapter);
        dialogType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if (adapterView.getItemAtPosition(pos) == "Price"){
                    dialogEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else{
                    dialogEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilter(dialogEdit.getText().toString(),dialogType.getSelectedItem().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setFilter(String filter , String filterType){
        searchResult = new ArrayList<>();
        SearchAsyncTask task;

        for(RealEstate realEstate : mData){
            if(filterType.equals("Type")){
                if(realEstate.getType().contains(filter)){
                    searchResult.add(realEstate);

                }
            } else {
                task = new SearchAsyncTask(this,filter,filterType);
                task.execute(realEstate);
                }
        }
        configureAdapter(searchResult);
    }


    class GetDataTask extends AsyncTask<Context, Void, ArrayList<RealEstate>>{
        @Override
        protected void onPostExecute(ArrayList<RealEstate> realEstates) {
            configureAdapter(realEstates);
            super.onPostExecute(realEstates);
            this.cancel(true);
        }

        @Override
        protected ArrayList<RealEstate> doInBackground(Context... contexts) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database").build();

            return (ArrayList<RealEstate>) db.realEstateDao().getAll();
        }
    }

    class SearchAsyncTask extends AsyncTask<RealEstate, Void, Object>{
        SearchAsyncResponse delegate;
        String mFilter, mFilterType;
        RealEstate parameter;

        private SearchAsyncTask(SearchAsyncResponse pInterface, String pFilter, String pFilterType){
            delegate = pInterface;
            mFilter = pFilter;
            mFilterType = pFilterType;
        }

        @Override
        protected void onPostExecute(Object aVoid) {
            switch (mFilterType){
                case "City": Address a = (Address) aVoid;
                if(a.getCity().equals(mFilter)){
                    delegate.positiveResult(parameter);
                }
                break;

                case "Price": try{ Price p = (Price) aVoid;
                if(String.valueOf(p.getValue()).equals(mFilter)) {
                    delegate.positiveResult(parameter);
                }}catch (ClassCastException e){
                    e.printStackTrace();
                }
                break;

            }

            super.onPostExecute(aVoid);
            this.cancel(true);
        }

        @Override
        protected Object doInBackground(RealEstate... realEstates) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database").build();
            parameter = realEstates[0];

            switch (mFilterType){
                case "City": return db.realEstateDao().getAddressById(realEstates[0].getAddressId());

                case "Price": return db.realEstateDao().getPriceById(realEstates[0].getPriceId());
            }

            return null;
        }
    }
}
interface SearchAsyncResponse{
        void positiveResult(RealEstate pRealEstate);
        void negativeResult();
}
