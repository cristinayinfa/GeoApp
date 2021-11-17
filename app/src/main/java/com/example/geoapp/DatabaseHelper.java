package com.example.geoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String LOCATION_TABLE = "LOCATION_TABLE";
    public static final String COL_ID = "ID";
    public static final String COL_ADDRESS = "ADDRESS";
    public static final String COL_LONG = "LONGITUDE";
    public static final String COL_LAT = "LATITUDE";
    private final Context context;

    DatabaseHelper(@Nullable Context context) {
        super(context, "geoapp.db", null, 1);
        this.context = context;
    }

    // Called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + LOCATION_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ADDRESS + " TEXT ," + COL_LONG + " TEXT, " + COL_LAT + " TEXT )";
        db.execSQL(createTableStatement);
    }

    // this is called if the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ LOCATION_TABLE);
        onCreate(db);
    }

    // Function to add a new location
    public boolean addOne(LocationModel locationModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ADDRESS, locationModel.getAddress());
        cv.put(COL_LONG, locationModel.getLongitude());
        cv.put(COL_LAT, locationModel.getLatitude());
        long insert = db.insert(LOCATION_TABLE, null, cv);
        if(insert == -1){
            return false;
        } else{
            return true;
        }
    }

    // Function to get all items in the database and store in a cursor
    Cursor readAll(){
        String query = "SELECT * FROM " +LOCATION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Function to update data as a new location but same id
    void updateLocation(String row_id, String address, String longitude, String latitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ADDRESS, address);
        cv.put(COL_LONG, longitude);
        cv.put(COL_LAT, latitude);

        long update = db.update(LOCATION_TABLE, cv, "id=?", new String[]{row_id});
        if(update == -1){
           Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteLocation(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long remove = db.delete(LOCATION_TABLE, "id=?", new String[]{row_id});
        if(remove == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
