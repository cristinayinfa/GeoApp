package com.example.geoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddLocation extends AppCompatActivity {

    EditText location;
    Button search;
    TextView result;
    Button add;

    String addressStr;
    String latitudeStr;
    String longitudeStr;
    String resultStr;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        location = findViewById(R.id.location_editText);
        search = findViewById(R.id.search_button);
        result = findViewById(R.id.result_textView);
        add = findViewById(R.id.add_button);

        // Search button onClick listener
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String address = location.getText().toString();
                if(address.isEmpty()) {
                    result.setText("No location entered :(");
                } else{
                    GeoLocation geoLocation = new GeoLocation();
                    GeoLocation.getAddress(address, getApplicationContext(), new GeoHandler());
                }
            }
        });

        // Add button onClick listener
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationModel locationModel;
               if(addressStr == null) {
                   Toast.makeText(AddLocation.this, "Please choose a valid location first", Toast.LENGTH_SHORT).show();
               }else{
                   locationModel = new LocationModel(-1, addressStr, longitudeStr, latitudeStr);
                   DatabaseHelper dbHelper = new DatabaseHelper(AddLocation.this);
                   boolean success = dbHelper.addOne(locationModel);

                   if(success) {
                       Toast.makeText(AddLocation.this, "Location added (:", Toast.LENGTH_SHORT).show();
                   }
               }

            }
        });

    }

    // Message handler for search results
    private class GeoHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    addressStr = bundle.getString("address");
                    latitudeStr = bundle.getString("latitude");
                    longitudeStr = bundle.getString("longitude");
                    resultStr = "Address: " + addressStr + "\nLongitude: " + longitudeStr + "\nLatitude: " + latitudeStr ;

                    break;
                default:
                    addressStr = null;
                    latitudeStr = null;
                    longitudeStr = null;
                    resultStr = "Location not found! :(";
            }
            result.setText(resultStr);
        }
    }

}