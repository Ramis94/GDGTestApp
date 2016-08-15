package com.gramis.gdgtestapp.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.gramis.gdgtestapp.R;
import com.gramis.gdgtestapp.model.City;

import java.util.ArrayList;

/**
 * Created by GRamis on 13.07.2016.
 */
public class CityAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<City> cities;

    public CityAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item, parent, false);
        }

        City city = getCity(position);

        // заполняем View
        ((TextView) view.findViewById(R.id.tvCity)).setText(city.getName());
        ((TextView) view.findViewById(R.id.tempCity)).setText(city.getTemperature());

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(city.isBox());
        return view;
    }

    // товар по позиции
    City getCity(int position) {
        return ((City) getItem(position));
    }

    // содержимое корзины
    public ArrayList<City> getBox() {
        ArrayList<City> box = new ArrayList<>();
        for (City p : cities) {
            // если в корзине
            if (p.isBox())
                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getCity((Integer) buttonView.getTag()).setBox(isChecked);
        }
    };
}
