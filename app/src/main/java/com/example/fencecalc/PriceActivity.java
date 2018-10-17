package com.example.fencecalc;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PriceActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    public static final String PRICE_PROF_SHEET = "price_prof_sheet", PRICE_60x60 = "price_60x60", PRICE_80x80 = "price_80x80",
            PRICE_40x20 = "price_40x20", PREFERENCES = "my_preferences";

    EditText etPriceProfSheet, etPrice60x60, etPrice80x80, etPrice40x20;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        etPriceProfSheet = findViewById(R.id.etPriceProfSheet);
        etPriceProfSheet.setOnFocusChangeListener(this);
        etPrice60x60 = findViewById(R.id.etPrice60x60);
        etPrice60x60.setOnFocusChangeListener(this);
        etPrice80x80 = findViewById(R.id.etPrice80x80);
        etPrice80x80.setOnFocusChangeListener(this);
        etPrice40x20 = findViewById(R.id.etPrice40x20);
        etPrice40x20.setOnFocusChangeListener(this);

        preferences = getSharedPreferences(PREFERENCES,MODE_PRIVATE);

        etPriceProfSheet.setText(preferences.getString(PRICE_PROF_SHEET, ""));
        etPrice60x60.setText(preferences.getString(PRICE_60x60, ""));
        etPrice80x80.setText(preferences.getString(PRICE_80x80, ""));
        etPrice40x20.setText(preferences.getString(PRICE_40x20, ""));

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        preferences = getSharedPreferences(PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        switch (view.getId()) {
            case R.id.etPriceProfSheet:
                editor.putString(PRICE_PROF_SHEET,etPriceProfSheet.getText().toString());
                editor.commit();
                break;
            case R.id.etPrice60x60:
                editor.putString(PRICE_60x60,etPrice60x60.getText().toString());
                editor.commit();
                break;
            case R.id.etPrice80x80:
                editor.putString(PRICE_80x80,etPrice80x80.getText().toString());
                editor.commit();
                break;
            case R.id.etPrice40x20:
                editor.putString(PRICE_40x20,etPrice40x20.getText().toString());
                editor.commit();
                break;
            default:
                break;
        }
    }
}
