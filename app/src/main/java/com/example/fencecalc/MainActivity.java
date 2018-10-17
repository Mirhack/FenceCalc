package com.example.fencecalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener,
        CompoundButton.OnCheckedChangeListener {
    EditText etNumSides, etSide1, etSide2, etSide3, etSide4, etSectionLength, etGateWidth;
    Button btnGetResult;
    TextView tvSide1, tvSide2, tvSide3, tvSide4, tvGateWidth;
    Switch swDoor, swGate, swClosedFence;
    boolean isClosedFence = false, hasDoor = false, hasGate = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Установка цен");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                startActivity(new Intent(this, PriceActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumSides = findViewById(R.id.etNumSides);
        etNumSides.setOnFocusChangeListener(this);

        etSide1 = findViewById(R.id.etSide1);
        etSide2 = findViewById(R.id.etSide2);
        etSide3 = findViewById(R.id.etSide3);
        etSide4 = findViewById(R.id.etSide4);
        etSectionLength = findViewById(R.id.etSectionLength);
        etGateWidth = findViewById(R.id.etGateWidth);

        tvSide1 = findViewById(R.id.tvSide1);
        tvSide2 = findViewById(R.id.tvSide2);
        tvSide3 = findViewById(R.id.tvSide3);
        tvSide4 = findViewById(R.id.tvSide4);
        tvGateWidth = findViewById(R.id.tvGateWidth);


        swClosedFence = findViewById(R.id.swClosedFence);
        swClosedFence.setOnCheckedChangeListener(this);

        swDoor = findViewById(R.id.swDoor);
        swDoor.setOnCheckedChangeListener(this);

        swGate = findViewById(R.id.swGate);
        swGate.setOnCheckedChangeListener(this);


        btnGetResult = findViewById(R.id.btnGetResult);
        btnGetResult.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetResult:
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("numSides", Integer.parseInt(etNumSides.getText().toString()));
                intent.putExtra("side1", Float.parseFloat(etSide1.getText().toString()));
                intent.putExtra("side2", Float.parseFloat(etSide2.getText().toString()));
                intent.putExtra("side3", Float.parseFloat(etSide3.getText().toString()));
                intent.putExtra("side4", Float.parseFloat(etSide4.getText().toString()));
                intent.putExtra("isClosedFence", isClosedFence);
                intent.putExtra("sectionLength", Float.parseFloat(etSectionLength.getText().toString()));
                intent.putExtra("hasDoor", hasDoor);
                intent.putExtra("hasGate", hasGate);
                intent.putExtra("gateWidth", Float.parseFloat(etGateWidth.getText().toString()));
                startActivity(intent);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        try {
            switch (view.getId()) {
                case R.id.etNumSides:
                    if (!hasFocus) {
                        switch (Integer.valueOf(etNumSides.getText().toString())) {
                            case 1:
                                etSide2.setEnabled(false);
                                tvSide2.setEnabled(false);
                                etSide3.setEnabled(false);
                                tvSide3.setEnabled(false);
                                etSide4.setEnabled(false);
                                tvSide4.setEnabled(false);
                                swClosedFence.setEnabled(false);
                                break;
                            case 2:
                                etSide2.setEnabled(true);
                                tvSide2.setEnabled(true);
                                etSide3.setEnabled(false);
                                tvSide3.setEnabled(false);
                                etSide4.setEnabled(false);
                                tvSide4.setEnabled(false);
                                swClosedFence.setEnabled(false);
                                break;
                            case 3:
                                etSide2.setEnabled(true);
                                tvSide2.setEnabled(true);
                                etSide3.setEnabled(true);
                                tvSide3.setEnabled(true);
                                etSide4.setEnabled(false);
                                tvSide4.setEnabled(false);
                                swClosedFence.setEnabled(true);
                                break;
                            case 4:
                                etSide2.setEnabled(true);
                                tvSide2.setEnabled(true);
                                etSide3.setEnabled(true);
                                tvSide3.setEnabled(true);
                                etSide4.setEnabled(true);
                                tvSide4.setEnabled(true);
                                swClosedFence.setEnabled(true);
                                break;
                            default:
                                Toast.makeText(this, "Введите количество сторон, от 1 до 4", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Введите количество сторон, от 1 до 4", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.swClosedFence:
                isClosedFence = isChecked;
                break;
            case R.id.swDoor:
                hasDoor = isChecked;
                break;
            case R.id.swGate:
                hasGate = isChecked;
                etGateWidth.setEnabled(isChecked);
                tvGateWidth.setEnabled(isChecked);
                break;
            default:
                break;
        }
    }
}
