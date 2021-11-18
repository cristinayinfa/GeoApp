package com.example.geoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText location;
    Button search, update, delete;
    TextView result;
    String id, address, longitude, latitude;

    String addressStr;
    String latitudeStr;
    String longitudeStr;
    String resultStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        location = findViewById(R.id.location_editText2);
        result = findViewById(R.id.result_textView2);
        search = findViewById(R.id.search_button2);
        update = findViewById(R.id.update_button);
        delete = findViewById(R.id.delete_button);
        getIntentData();

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

        // Update button onClick listener
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressStr == null) {
                    Toast.makeText(UpdateActivity.this, "Please choose a valid location first", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper dbHelper = new DatabaseHelper(UpdateActivity.this);
                    dbHelper.updateLocation(id, addressStr, longitudeStr, latitudeStr);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    // Get data from intent to render into the fields
    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("address") && getIntent().hasExtra("longitude") && getIntent().hasExtra("latitude")){
            id = getIntent().getStringExtra("id");
            address = getIntent().getStringExtra("address");
            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");
            location.setText(address);
            result.setText("Address: " + address + "\nLongitude: " + longitude +  "\nLatitude: " + latitude);
        } else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    // Message handler for search results
    private class GeoHandler extends Handler {
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

    // Prompt confirmation dialog for deletion
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete " + address + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper dbHelper = new DatabaseHelper(UpdateActivity.this);
                dbHelper.deleteLocation(id);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}
