package com.gramis.gdgtestapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.gramis.gdgtestapp.model.City;
import com.gramis.gdgtestapp.model.Weather;

import org.json.JSONException;

public class WeatherActivity extends AppCompatActivity {

    private TextView cityText;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;

    private TextView hum;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        String city = getIntent().getExtras().getString("city");

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(city);

        if(city != null) {
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

            String selection = "city = ?";
            String[] selectionArgs = new String[]{city};
            Cursor cursor = sqLiteDatabase.query("CityTable", null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {

                int cityColIndex = cursor.getColumnIndex("city");
                int temperatureColIndex = cursor.getColumnIndex("temperature");
                int humidityColIndex = cursor.getColumnIndex("humidity");
                int pressureColIndex = cursor.getColumnIndex("pressure");
                int speed_windColIndex = cursor.getColumnIndex("speed_wind");
                int deg_windColIndex = cursor.getColumnIndex("deg_wind");

                do {
                    setContentView(R.layout.activity_weather);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    setSupportActionBar(toolbar);

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    cityText = (TextView) findViewById(R.id.cityText);
                    temp = (TextView) findViewById(R.id.temp);
                    hum = (TextView) findViewById(R.id.hum);
                    press = (TextView) findViewById(R.id.press);
                    windSpeed = (TextView) findViewById(R.id.windSpeed);
                    windDeg = (TextView) findViewById(R.id.windDeg);

                    cityText.setText(cursor.getString(cityColIndex));
                    temp.setText(cursor.getString(temperatureColIndex));
                    hum.setText(cursor.getString(humidityColIndex));
                    press.setText(cursor.getString(pressureColIndex));
                    windSpeed.setText(cursor.getString(speed_windColIndex));
                    windDeg.setText(cursor.getString(deg_windColIndex));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = new WeatherHttpClient().getWeatherData(params[0]);

            try {
                weather = JSONWeatherParser.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            dbHelper = new DBHelper(WeatherActivity.this);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            String city = "";
            String temperature = "";
            String humidity = "";
            String pressure = "";
            String speed_wind = "";
            String deg_wind = "";

            if (weather == null){
                temperature = "Check the city!!!";
            } else {
                city = weather.location.getCity();
                temperature = "" + Math.round((weather.temperature.getTemp() - 273.15)) + " C";
                humidity = "" + weather.currentCondition.getHumidity() + "%";
                pressure = "" + weather.currentCondition.getPressure() + " hPa";
                speed_wind = "" + weather.wind.getSpeed() + " mps";
                deg_wind = "" + weather.wind.getDeg();
            }
            contentValues.put("temperature", temperature);
            contentValues.put("humidity", humidity);
            contentValues.put("pressure", pressure);
            contentValues.put("speed_wind", speed_wind);
            contentValues.put("deg_wind", deg_wind);

            sqLiteDatabase.update("CityTable", contentValues, "city = ?", new String[]{city});
        }
    }

}
