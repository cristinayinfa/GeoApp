package com.example.geoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

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

        for(int i=0; i<50; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ADDRESS, locations[i].getAddress());
            cv.put(COL_LONG, locations[i].getLongitude());
            cv.put(COL_LAT, locations[i].getLatitude());
            db.insert(LOCATION_TABLE, null, cv);
        }

    }

    // this is called if the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE);
        onCreate(db);
    }

    // Function to add a new location
    public boolean addOne(LocationModel locationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ADDRESS, locationModel.getAddress());
        cv.put(COL_LONG, locationModel.getLongitude());
        cv.put(COL_LAT, locationModel.getLatitude());
        long insert = db.insert(LOCATION_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    // Function to get all items in the database and store in a cursor
    Cursor readAll() {
        String query = "SELECT * FROM " + LOCATION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Function to update data as a new location but same id
    void updateLocation(String row_id, String address, String longitude, String latitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ADDRESS, address);
        cv.put(COL_LONG, longitude);
        cv.put(COL_LAT, latitude);

        long update = db.update(LOCATION_TABLE, cv, "id=?", new String[]{row_id});
        if (update == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to delete specific item
    void deleteLocation(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long remove = db.delete(LOCATION_TABLE, "id=?", new String[]{row_id});
        if (remove == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void readDB() throws IOException {
        // pass the path to the file as a parameter

            File file = new File(
                    "C:\\Users\\yinfa\\AndroidStudioProjects\\GeoApp\\app\\src\\main\\assets\\databases\\data.txt");
            Scanner sc = new Scanner(file);
            String line = " ";
            String[] splitted;
            int i = 0;
            while (sc.hasNextLine()) {
                i++;
                line = sc.nextLine();
                Toast.makeText(context, line, Toast.LENGTH_SHORT).show();
                splitted = line.split("\\s+");
                LocationModel locItem = new LocationModel(i, splitted[0], splitted[1], splitted[2]);
                addOne(locItem);
        }
    }

    // hardcoded deafault database
    private LocationModel[] locations = {
            new LocationModel(1,"panama", "-80.782127", "8.537981"),
            new LocationModel(2,"taipei","121.56542680000001","-25.0329636"),
            new LocationModel(1,"toronto", "-79.3831843", "43.653226"),
            new LocationModel(1,"tokyo", "139.65031059999998", "35.6761919"),
            new LocationModel(1,"amsterdam", "4.9041388999999995", "52.3675734"),
            new LocationModel(1,"osaka", "135.5022535", "34.6937249"),
            new LocationModel(1,"paris", "2.3522219000000004", "48.856614"),
            new LocationModel(1,"san francisco", "-122.41941550000001", "37.7749295"),
            new LocationModel(1,"los angeles", "-118.24368489999999", "34.0522342"),
            new LocationModel(1,"florida", "-81.5157535", "27.6648274"),
            new LocationModel(1,"ho chi mih", "106.62966379999999", "10.8230989"),
            new LocationModel(1,"hanoi", "105.8341598", "21.0277644"),
            new LocationModel(1,"guangzhou", "113.26438499999999", "23.12911"),
            new LocationModel(1,"madrid", "-3.7037902", "40.4167754"),
            new LocationModel(1,"london", "-0.12758619999999998", "51.5072178"),
            new LocationModel(1,"seattle", "-122.3320708", "47.6062095"),
            new LocationModel(1,"texas", "-99.9018131", "31.968598800000002"),
            new LocationModel(1,"montreal", "-73.567256", "45.5016889"),
            new LocationModel(1,"vancouver", "-123.1207375", "49.2827291"),
            new LocationModel(1,"nagano", "138.1950371", "36.6485258"),
            new LocationModel(1,"seoul", "126.97796919999999", "37.566535"),
            new LocationModel(1,"boquete", "-82.4481944", "8.777231800000001"),
            new LocationModel(1,"khazakstan", "66.923684", "48.019573"),
            new LocationModel(1,"xian", "108.93977", "34.341574"),
            new LocationModel(1,"kyoto", "135.7681489", "35.011564"),
            new LocationModel(1,"hong kong", "114.16936109999999", "22.3193039"),
            new LocationModel(1,"taichung", "120.67364819999999", "24.1477358"),
            new LocationModel(1,"tainan", "120.22687579999999", "22.9998999"),
            new LocationModel(1,"manila", "120.98421949999998", "14.5995124"),
            new LocationModel(1,"pyongyang", "125.7625241", "39.0392193"),
            new LocationModel(1,"rome", "12.4963655", "41.9027835"),
            new LocationModel(1,"rio de janeiro", "-43.1728965", "-22.9068467"),
            new LocationModel(1,"darien", "-73.4686858", "41.0771914"),
            new LocationModel(1,"veraguas", "-81.0754657", "8.1231033"),
            new LocationModel(1,"santiago", "-70.6692655", "-33.448889699999995"),
            new LocationModel(1,"quito", "-78.4678382", "-0.18065319999999999"),
            new LocationModel(1,"san jose", "-121.8863286", "37.3382082"),
            new LocationModel(1,"medellin", "-75.56581530000001", "6.2476376"),
            new LocationModel(1,"bogota", "-74.072092", "4.710988599999999"),
            new LocationModel(1,"temecula", "-117.1483648", "33.493639099999996"),
            new LocationModel(1,"buenos aires", "-58.3815591", "-34.6036844"),
            new LocationModel(1,"budapest", "19.040235", "47.497912"),
            new LocationModel(1,"palawan", "118.7383615", "9.8349493"),
            new LocationModel(1,"mikonos", "25.328862299999997", "37.446718499999996"),
            new LocationModel(1,"munich", "11.5819805", "48.1351253"),
            new LocationModel(1,"valencia", "-0.37628809999999996", "39.4699075"),
            new LocationModel(1,"lisbon", "-9.1393366", "38.722252399999995"),
            new LocationModel(1,"moscow", "37.6172999", "55.755826"),
            new LocationModel(1,"prague", "14.437800500000002", "50.075538099999996"),
            new LocationModel(1,"cairo", "31.2357116", "30.0444196"),
    };
}
