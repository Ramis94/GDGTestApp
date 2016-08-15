package com.gramis.gdgtestapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gramis.gdgtestapp.adapter.CityAdapter;
import com.gramis.gdgtestapp.model.City;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";
    ArrayList<City> cities = new ArrayList<>();
    CityAdapter cityAdapter;
    DBHelper dbHelper;
    DialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        dialogFragment = new DialogFragment();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getFragmentManager(), "dlg1");
            }
        });

        fillData();
        cityAdapter = new CityAdapter(this, cities);

        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        if (lvMain != null) {
            lvMain.setAdapter(cityAdapter);
        }

        assert lvMain != null;
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);

                Intent intent = new Intent(view.getContext(), WeatherActivity.class);
                intent.putExtra("city", cities.get((int) id).getName());
                startActivity(intent);
            }
        });
    }

    // генерируем данные для адаптера
    void fillData() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = sqLiteDatabase.query("CityTable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int cityColIndex = c.getColumnIndex("city");
            int temperatureColIndex = c.getColumnIndex("temperature");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                cities.add(new City(c.getInt(idColIndex), c.getString(cityColIndex), c.getString(temperatureColIndex), false));
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(cityColIndex) +
                                ", temperature = " + c.getString(temperatureColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }

    // выводим информацию о корзине
    public void deleteCity(View v) {
        dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String result = "Удалены:";
        for (City city : cityAdapter.getBox()) {
            if (city.isBox()) {
                result += "\n" + city.getName();
                sqLiteDatabase.delete("CityTable", "id = " + city.getId(), null);
            }
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        sqLiteDatabase.close();
        Intent intent = new Intent(this, MainActivity.class);
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
