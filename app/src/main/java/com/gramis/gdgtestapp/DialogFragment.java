package com.gramis.gdgtestapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gramis.gdgtestapp.model.City;

/**
 * Created by GRamis on 22.07.2016.
 */
public class DialogFragment extends android.app.DialogFragment implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";
    EditText editText;
    String s;
    DBHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Add city");
        View v = inflater.inflate(R.layout.dialog_add, null);
        v.findViewById(R.id.btnOk).setOnClickListener(this);
        v.findViewById(R.id.btnCancel).setOnClickListener(this);
        editText = (EditText) v.findViewById(R.id.editAdd);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOk:
                dbHelper = new DBHelper(getActivity());
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                if (editText != null) {
                    String name = editText.getText().toString();
                    Log.e(LOG_TAG, name);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("city", name);
                    sqLiteDatabase.insert("CityTable", null, contentValues);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.e(LOG_TAG, "Dlf Null");
                }
                break;
            case R.id.btnCancel:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
