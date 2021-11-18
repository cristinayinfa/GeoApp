package com.example.geoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ArrayList<String> loc_id, loc_address, loc_long, loc_lat;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView locationsList = findViewById(R.id.locations_list);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        // Add new location button onClick listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddLocation();
            }
        });

        // Display locations in the database
        dbHelper = new DatabaseHelper(MainActivity.this);
        loc_id = new ArrayList<>();
        loc_address= new ArrayList<>();
        loc_long = new ArrayList<>();
        loc_lat = new ArrayList<>();

        customAdapter = new CustomAdapter(MainActivity.this , this, loc_id, loc_address, loc_long, loc_lat);
        storeArray();

        locationsList.setAdapter(customAdapter);
        locationsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Do search once the user enters characters and filter search everytime the text changes
        SearchView searchList = findViewById(R.id.searchbar);
        searchList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> filtered_address = new ArrayList<String>();
                ArrayList<String> filtered_id = new ArrayList<String>();
                ArrayList<String> filtered_long = new ArrayList<String>();
                ArrayList<String> filtered_lat = new ArrayList<String>();

                for(int i = 0; i < loc_address.size(); i++){
                    if(loc_address.get(i).contains(s)){
                        filtered_address.add(loc_address.get(i));
                        filtered_id.add(loc_id.get(i));
                        filtered_long.add(loc_long.get(i));
                        filtered_lat.add(loc_lat.get(i));

                    }
                }
                customAdapter = new CustomAdapter(MainActivity.this , MainActivity.this, filtered_id, filtered_address, filtered_long, filtered_lat);
                locationsList.setAdapter(customAdapter);
                locationsList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    return false;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Refresh activity
        if(requestCode == 1){
            recreate();
        }
    }

    // Function that gets all information from database
    public void storeArray(){
        Cursor cursor = dbHelper.readAll();
        if(cursor.getCount() == 0){
            //no data in database
        } else{
            while (cursor.moveToNext()){
                // SHOULD REFORMAT STR]NG HERE FOR LONG AND LAT
                loc_id.add(cursor.getString(0));
                loc_address.add(cursor.getString(1));
                loc_long.add(cursor.getString(2));
                loc_lat.add(cursor.getString(3));
            }
        }
    }

    // Function intent for add location activity
    public void openAddLocation(){
        Intent intent = new Intent(this, AddLocation.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
