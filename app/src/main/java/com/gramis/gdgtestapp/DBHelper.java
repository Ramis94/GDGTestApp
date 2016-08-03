package com.gramis.gdgtestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by GRamis on 13.07.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("CREATE TABLE CityTable ("
                + "id integer primary key autoincrement,"
                + "city text"
                + ");");
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", "Kazan");
        db.insert("CityTable", null, contentValues);
        contentValues.put("city", "Moscow");
        db.insert("CityTable", null, contentValues);
        contentValues.put("city", "Saint Petersburg");
        db.insert("CityTable", null, contentValues);
        contentValues.put("city", "Nizhny Novgorod");
        db.insert("CityTable", null, contentValues);
        contentValues.put("city", "Voronezh");
        db.insert("CityTable", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
