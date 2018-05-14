package com.ravilla.abhi.autofill.Ldispaly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ravilla.abhi.autofill.R;
import com.ravilla.abhi.autofill.data.datastore;
import com.ravilla.abhi.autofill.encryption.decrypt;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity{
    private AppCompatActivity activity = list.this;
    Context context = list.this;
    private RecyclerView recyclerViewlist;
    private ArrayList<fulllist> listBeneficiary;
    private listAdapter listAdapter;
    private datastore databaseHelper;
    private ArrayList<fulllist> filteredList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        Log.i("Flow","Starting Intialization Methods");
        initViews();
        initObjects();

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewlist = (RecyclerView) findViewById(R.id.recyclerview_list);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        Log.i("Flow","In initObjects");
        listBeneficiary = new ArrayList<>();
        listAdapter = new listAdapter(listBeneficiary, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewlist.setLayoutManager(mLayoutManager);
        recyclerViewlist.setItemAnimator(new DefaultItemAnimator());
        recyclerViewlist.setHasFixedSize(true);
        recyclerViewlist.setAdapter(listAdapter);
        databaseHelper = new datastore(activity);
        getDataFromSQLite();
    }
    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBeneficiary.clear();
                List<fulllist> lfull = databaseHelper. getAll();
                decrypt dc = new decrypt();
                for (fulllist fl:lfull){
                  fl.setsite(dc.decrypt(fl.getsite(),key()));
                  fl.setUname(dc.decrypt(fl.getuname(),key()));
                  fl.setPassword(dc.decrypt(fl.getpassword(),key()));
                }
                listBeneficiary.addAll(lfull);
                Log.i("Flow","returned list size "+lfull.size());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
    private String key(){
        SharedPreferences sharedPref = getSharedPreferences(
                "User", this.MODE_PRIVATE);
        final String passphrase = sharedPref.getString("userid", "none");
        return passphrase;
    }

}
